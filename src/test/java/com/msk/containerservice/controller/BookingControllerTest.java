package com.msk.containerservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
class BookingControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void checkAvailability_shouldReturnOk() {
        String requestBody = """
                {
                    "containerType": "DRY",
                    "containerSize": 20,
                    "origin": "Southampton",
                    "destination": "Singapore",
                    "quantity": 5
                }
                """;

        webTestClient
                .post()
                .uri("/api/bookings/checkAvailability")
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.available").exists();
    }
}
