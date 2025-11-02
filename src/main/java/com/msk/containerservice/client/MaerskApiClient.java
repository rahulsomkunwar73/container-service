package com.msk.containerservice.client;

import com.msk.containerservice.dto.AvailabilityRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Component
public class MaerskApiClient {

    private final WebClient webClient;
    private final String checkAvailabilityEndpoint;
    private final Duration timeout;

    public MaerskApiClient(
            @Value("${external.maersk.api.base-url}") String baseUrl,
            @Value("${external.maersk.api.check-availability-endpoint}") String endpoint,
            @Value("${external.maersk.api.timeout-seconds:10}") int timeoutSeconds,
            WebClient.Builder webClientBuilder) {

        this.timeout = Duration.ofSeconds(timeoutSeconds);
        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();
        this.checkAvailabilityEndpoint = endpoint;

        log.info("MaerskApiClient initialized with baseUrl: {}, timeout: {}s", baseUrl, timeoutSeconds);
    }

    public Mono<Integer> getAvailableSpace(AvailabilityRequest request) {
        log.info("Calling external API for request: {}", request);

        return webClient
                .post()
                .uri(checkAvailabilityEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ExternalApiResponse.class)
                .timeout(timeout)
                .map(response -> {
                    log.info("Received availableSpace={}", response.availableSpace());
                    return response.availableSpace();
                })
                .doOnError(error ->
                        log.error("Error calling external API: {}", error.getMessage()));
    }

    // Response from external API
    record ExternalApiResponse(Integer availableSpace) {
    }
}
