package com.msk.containerservice.controller;

import com.msk.containerservice.dto.AvailabilityResponse;
import com.msk.containerservice.service.AvailabilityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
class BookingControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AvailabilityService availabilityService;

    @Test
    void checkAvailability_validRequest_returnsOk() {
        // Given: Service returns available=true
        when(availabilityService.checkAvailability(any()))
                .thenReturn(Mono.just(new AvailabilityResponse(true)));

        String requestBody = """
                {
                    "containerType": "DRY",
                    "containerSize": 20,
                    "origin": "Southampton",
                    "destination": "Singapore",
                    "quantity": 5
                }
                """;

        // When: POST to endpoint
        // Then: Should return 200 OK with available=true
        webTestClient
                .post()
                .uri("/api/bookings/checkAvailability")
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.available").isEqualTo(true);
    }

    @Test
    void checkAvailability_invalidContainerSize_returns400() {
        // Given: Invalid containerSize (30, not 20 or 40)
        String requestBody = """
                {
                    "containerType": "DRY",
                    "containerSize": 30,
                    "origin": "Southampton",
                    "destination": "Singapore",
                    "quantity": 5
                }
                """;

        // When: POST to endpoint
        // Then: Should return 400 Bad Request
        webTestClient
                .post()
                .uri("/api/bookings/checkAvailability")
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void checkAvailability_missingContainerType_returns400() {
        // Given: Missing containerType field
        String requestBody = """
                {
                    "containerSize": 20,
                    "origin": "Southampton",
                    "destination": "Singapore",
                    "quantity": 5
                }
                """;

        // When: POST to endpoint
        // Then: Should return 400 Bad Request
        webTestClient
                .post()
                .uri("/api/bookings/checkAvailability")
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void checkAvailability_originTooShort_returns400() {
        // Given: Origin less than 5 characters
        String requestBody = """
                {
                    "containerType": "DRY",
                    "containerSize": 20,
                    "origin": "NYC",
                    "destination": "Singapore",
                    "quantity": 5
                }
                """;

        // When: POST to endpoint
        // Then: Should return 400 Bad Request
        webTestClient
                .post()
                .uri("/api/bookings/checkAvailability")
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void checkAvailability_quantityTooHigh_returns400() {
        // Given: Quantity exceeds 100
        String requestBody = """
                {
                    "containerType": "DRY",
                    "containerSize": 20,
                    "origin": "Southampton",
                    "destination": "Singapore",
                    "quantity": 150
                }
                """;

        // When: POST to endpoint
        // Then: Should return 400 Bad Request
        webTestClient
                .post()
                .uri("/api/bookings/checkAvailability")
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void checkAvailability_quantityZero_returns400() {
        // Given: Quantity is 0
        String requestBody = """
                {
                    "containerType": "DRY",
                    "containerSize": 20,
                    "origin": "Southampton",
                    "destination": "Singapore",
                    "quantity": 0
                }
                """;

        // When: POST to endpoint
        // Then: Should return 400 Bad Request
        webTestClient
                .post()
                .uri("/api/bookings/checkAvailability")
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isBadRequest();
    }
}
