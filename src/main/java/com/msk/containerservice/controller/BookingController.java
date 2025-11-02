package com.msk.containerservice.controller;

import com.msk.containerservice.dto.AvailabilityRequest;
import com.msk.containerservice.dto.AvailabilityResponse;
import com.msk.containerservice.dto.BookingRequest;
import com.msk.containerservice.dto.BookingResponse;
import com.msk.containerservice.service.AvailabilityService;
import com.msk.containerservice.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {

    private final AvailabilityService availabilityService;

    private final BookingService bookingService;


    @PostMapping("/checkAvailability")
    public Mono<AvailabilityResponse> checkAvailability(@Valid @RequestBody AvailabilityRequest request) {
        return availabilityService.checkAvailability(request)
                .doOnSuccess(response ->
                        log.info("Availability check result: {}", response));
    }

    @PostMapping
    public Mono<BookingResponse> createBooking(
            @Valid @RequestBody BookingRequest request) {
        return bookingService.createBooking(request)
                .doOnSuccess(response ->
                        log.info("Booking created: {}", response));
    }
}
