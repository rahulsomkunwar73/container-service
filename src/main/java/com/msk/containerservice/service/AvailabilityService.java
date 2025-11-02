package com.msk.containerservice.service;

import com.msk.containerservice.client.MaerskApiClient;
import com.msk.containerservice.dto.AvailabilityRequest;
import com.msk.containerservice.dto.AvailabilityResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final MaerskApiClient maerskApiClient;


    public Mono<AvailabilityResponse> checkAvailability(AvailabilityRequest request) {
        log.info("Service: Checking availability for request: {}", request);

        return maerskApiClient.getAvailableSpace(request)
                .map(availableSpace -> {
                    boolean available = availableSpace > 0;
                    log.info("Service: availableSpace={}, available={}", availableSpace, available);
                    return new AvailabilityResponse(available);
                })
                .doOnError(error ->
                        log.error("Service: Error checking availability", error));
    }
}
