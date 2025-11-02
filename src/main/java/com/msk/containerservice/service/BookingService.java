package com.msk.containerservice.service;

import com.msk.containerservice.dto.BookingRequest;
import com.msk.containerservice.dto.BookingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class BookingService {

    private final AtomicLong bookingCounter = new AtomicLong(957000001);

    public Mono<BookingResponse> createBooking(BookingRequest request) {
        log.info("Service: Creating booking for request: {}", request);

        String bookingRef = String.valueOf(bookingCounter.getAndIncrement());

        log.info("Service: Generated bookingRef={}", bookingRef);

        return Mono.just(new BookingResponse(bookingRef));
    }
}
