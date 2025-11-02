package com.msk.containerservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityRequest {

    @NotNull(message = "Container size is required")
    private Integer containerSize;

    @AssertTrue(message = "Container size must be 20 or 40")
    private boolean isValidContainerSize() {
        return containerSize != null && (containerSize == 20 || containerSize == 40);
    }

    @NotNull(message = "Container type is required")
    private ContainerType containerType;

    @NotBlank(message = "Origin is required")
    @Size(min = 5, max = 20, message = "Origin must be between 5 and 20 characters")
    private String origin;

    @NotBlank(message = "Destination is required")
    @Size(min = 5, max = 20, message = "Destination must be between 5 and 20 characters")
    private String destination;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 100, message = "Quantity must not exceed 100")
    private Integer quantity;
}
