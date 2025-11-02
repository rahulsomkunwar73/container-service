package com.msk.containerservice.service;

import com.msk.containerservice.client.MaerskApiClient;
import com.msk.containerservice.dto.AvailabilityRequest;
import com.msk.containerservice.dto.AvailabilityResponse;
import com.msk.containerservice.dto.ContainerType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * TDD Tests for AvailabilityService
 * Phase 2.3: Service layer tests
 *
 * Tests business logic:
 * - availableSpace > 0 → available = true
 * - availableSpace = 0 → available = false
 */
@ExtendWith(MockitoExtension.class)
class AvailabilityServiceTest {

    @Mock
    private MaerskApiClient maerskApiClient;

    @InjectMocks
    private AvailabilityService availabilityService;

    @Test
    void checkAvailability_whenSpaceAvailable_returnsTrue() {
        // Given: External API returns 6 available spaces
        AvailabilityRequest request = createValidRequest();
        when(maerskApiClient.getAvailableSpace(any()))
                .thenReturn(Mono.just(6));

        // When: Check availability
        Mono<AvailabilityResponse> result = availabilityService.checkAvailability(request);

        // Then: Should return available=true
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertTrue(response.isAvailable(), "Should be available when space > 0");
                })
                .verifyComplete();

        verify(maerskApiClient).getAvailableSpace(request);
    }

    @Test
    void checkAvailability_whenNoSpace_returnsFalse() {
        // Given: External API returns 0 available spaces
        AvailabilityRequest request = createValidRequest();
        when(maerskApiClient.getAvailableSpace(any()))
                .thenReturn(Mono.just(0));

        // When: Check availability
        Mono<AvailabilityResponse> result = availabilityService.checkAvailability(request);

        // Then: Should return available=false
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertFalse(response.isAvailable(), "Should not be available when space = 0");
                })
                .verifyComplete();

        verify(maerskApiClient).getAvailableSpace(request);
    }

    @Test
    void checkAvailability_whenLargeSpace_returnsTrue() {
        // Given: External API returns 100 available spaces
        AvailabilityRequest request = createValidRequest();
        when(maerskApiClient.getAvailableSpace(any()))
                .thenReturn(Mono.just(100));

        // When: Check availability
        Mono<AvailabilityResponse> result = availabilityService.checkAvailability(request);

        // Then: Should return available=true
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertTrue(response.isAvailable(), "Should be available for large space");
                })
                .verifyComplete();
    }

    @Test
    void checkAvailability_whenClientErrors_propagatesError() {
        // Given: Client returns error
        AvailabilityRequest request = createValidRequest();
        RuntimeException error = new RuntimeException("External API failed");
        when(maerskApiClient.getAvailableSpace(any()))
                .thenReturn(Mono.error(error));

        // When: Check availability
        Mono<AvailabilityResponse> result = availabilityService.checkAvailability(request);

        // Then: Should propagate error
        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                throwable.getMessage().equals("External API failed"))
                .verify();
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
