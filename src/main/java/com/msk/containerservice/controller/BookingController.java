package com.msk.containerservice.controller;

import com.msk.containerservice.dto.AvailabilityRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @PostMapping("/checkAvailability")
    public Mono<Map<String, Boolean>> checkAvailability(@Valid @RequestBody AvailabilityRequest request) {
        return Mono.just(Map.of("available", true));
    }
}
