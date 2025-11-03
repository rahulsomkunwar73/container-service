package com.msk.containerservice.model;

import com.msk.containerservice.dto.ContainerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookings")
public class Booking {

    @Id
    private String bookingRef;

    private Integer containerSize;
    private ContainerType containerType;
    private String origin;
    private String destination;
    private Integer quantity;
    private String timestamp;
}
