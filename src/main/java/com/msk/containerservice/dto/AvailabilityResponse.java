package com.msk.containerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for container availability check
 * Phase 2.2: Simple response with available boolean
 *
 * Response format from requirements:
 * { "available": true } or { "available": false }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityResponse {

    private boolean available;
}
