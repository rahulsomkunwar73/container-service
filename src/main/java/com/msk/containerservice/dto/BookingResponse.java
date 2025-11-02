package com.msk.containerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for booking creation
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private String bookingRef;
}
