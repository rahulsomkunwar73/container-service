package com.msk.containerservice.client;

import com.msk.containerservice.dto.AvailabilityRequest;
import com.msk.containerservice.dto.ContainerType;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for MaerskApiClient
 */
class MaerskApiClientTest {

    private MockWebServer mockWebServer;
    private MaerskApiClient client;

    @BeforeEach
    void setUp() throws IOException {
        // Setup mock external API server
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        // Create client pointing to mock server
        String baseUrl = mockWebServer.url("/").toString();
        client = new MaerskApiClient(
                baseUrl,
                "/bookings/checkAvailable",
                10, // timeout in seconds
                WebClient.builder()
        );
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getAvailableSpace_success_returnsSpace() {
        // Mock API returns 6
        mockWebServer.enqueue(new MockResponse()
                .setBody("{\"availableSpace\": 6}")
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json"));

        AvailabilityRequest request = createValidRequest();

        // Call client
        Mono<Integer> result = client.getAvailableSpace(request);

        // Verify result
        StepVerifier.create(result)
                .expectNext(6)
                .verifyComplete();
    }

    @Test
    void getAvailableSpace_zeroSpace_returnsZero() {
        mockWebServer.enqueue(new MockResponse()
                .setBody("{\"availableSpace\": 0}")
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json"));

        Mono<Integer> result = client.getAvailableSpace(createValidRequest());

        StepVerifier.create(result)
                .expectNext(0)
                .verifyComplete();
    }

    private AvailabilityRequest createValidRequest() {
        return new AvailabilityRequest(
                20,
                ContainerType.DRY,
                "Southampton",
                "Singapore",
                5
        );
    }
}
