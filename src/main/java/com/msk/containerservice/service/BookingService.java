package com.msk.containerservice.service;

import com.msk.containerservice.dto.BookingRequest;
import com.msk.containerservice.dto.BookingResponse;
import com.msk.containerservice.model.Booking;
import com.msk.containerservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final AtomicLong bookingCounter = new AtomicLong(957000001);

    public Mono<BookingResponse> createBooking(BookingRequest request) {
        log.info("Service: Creating booking for request: {}", request);

        // Generate booking reference
        String bookingRef = String.valueOf(bookingCounter.getAndIncrement());

        log.info("Service: Generated bookingRef={}", bookingRef);

        // Create booking document
        Booking booking = new Booking(
                bookingRef,
                request.getContainerSize(),
                request.getContainerType(),
                request.getOrigin(),
                request.getDestination(),
                request.getQuantity(),
                request.getTimestamp()
        );

        // Save to MongoDB and return response
        return bookingRepository.save(booking)
                .doOnSuccess(saved -> log.info("Service: Saved booking to MongoDB: {}", saved))
                .map(saved -> new BookingResponse(saved.getBookingRef()));
    }

    public Mono<Booking> getBooking(String bookingRef) {
        log.info("Service: Fetching booking with ref: {}", bookingRef);
        return bookingRepository.findById(bookingRef)
                .doOnSuccess(booking -> log.info("Service: Found booking: {}", booking));
    }
}
