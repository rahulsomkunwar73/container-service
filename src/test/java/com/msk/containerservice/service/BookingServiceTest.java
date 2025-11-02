package com.msk.containerservice.service;

import com.msk.containerservice.dto.BookingRequest;
import com.msk.containerservice.dto.BookingResponse;
import com.msk.containerservice.dto.ContainerType;
import com.msk.containerservice.model.Booking;
import com.msk.containerservice.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BookingServiceTest {

    private BookingService bookingService;


    @Mock
    private BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {
        bookingService = new BookingService();
    }

    @Test
    void createBooking_savesToMongoAndReturnsBookingResponse() {
        // Given
        BookingRequest request = new BookingRequest(
                20,
                ContainerType.DRY,
                "Southampton",
                "Singapore",
                5,
                "2020-10-12T13:53:09Z"
        );

        Booking savedBooking = new Booking(
                "957000001",
                20,
                ContainerType.DRY,
                "Southampton",
                "Singapore",
                5,
                "2020-10-12T13:53:09Z"
        );

        when(bookingRepository.save(any(Booking.class)))
                .thenReturn(Mono.just(savedBooking));

        // When
        StepVerifier.create(bookingService.createBooking(request))
                .assertNext(response -> {
                    // Then
                    assertEquals("957000001", response.getBookingRef());
                })
                .verifyComplete();
    }

    @Test
    void createBooking_incrementsBookingRef() {
        // Given
        BookingRequest request = new BookingRequest(
                20,
                ContainerType.DRY,
                "Southampton",
                "Singapore",
                5,
                "2020-10-12T13:53:09Z"
        );

        // When: Create multiple bookings
        BookingResponse first = bookingService.createBooking(request).block();
        BookingResponse second = bookingService.createBooking(request).block();
        BookingResponse third = bookingService.createBooking(request).block();

        // Then: BookingRef should increment
        assertEquals("957000001", first.getBookingRef());
        assertEquals("957000002", second.getBookingRef());
        assertEquals("957000003", third.getBookingRef());
    }
}
