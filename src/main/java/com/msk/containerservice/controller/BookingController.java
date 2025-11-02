package com.msk.containerservice.controller;

import com.msk.containerservice.dto.AvailabilityRequest;
import com.msk.containerservice.dto.AvailabilityResponse;
import com.msk.containerservice.service.AvailabilityService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private  AvailabilityService availabilityService;

    @PostMapping("/checkAvailability")
    public Mono<AvailabilityResponse> checkAvailability(@Valid @RequestBody AvailabilityRequest request) {
        return availabilityService.checkAvailability(request)
                .doOnSuccess(response ->
                        log.debug("Availability check result: {}", response));
    }
}
