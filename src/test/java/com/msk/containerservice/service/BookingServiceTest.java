package com.msk.containerservice.service;

import com.msk.containerservice.dto.BookingRequest;
import com.msk.containerservice.dto.BookingResponse;
import com.msk.containerservice.dto.ContainerType;
import com.msk.containerservice.model.Booking;
import com.msk.containerservice.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingService = new BookingService(bookingRepository);
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

        // Mock repository to return booking with generated ref
        when(bookingRepository.save(any(Booking.class)))
                .thenAnswer(invocation -> {
                    Booking booking = invocation.getArgument(0);
                    return Mono.just(booking);
                });

        // When: Create multiple bookings
        BookingResponse first = bookingService.createBooking(request).block();
        BookingResponse second = bookingService.createBooking(request).block();
        BookingResponse third = bookingService.createBooking(request).block();

        // Then: BookingRef should increment
        assertEquals("957000001", first.getBookingRef());
        assertEquals("957000002", second.getBookingRef());
        assertEquals("957000003", third.getBookingRef());
    }

    @Test
    void getBooking_whenExists_returnsBooking() {
        // Given
        Booking booking = new Booking(
                "957000001",
                20,
                ContainerType.DRY,
                "Southampton",
                "Singapore",
                5,
                "2020-10-12T13:53:09Z"
        );

        when(bookingRepository.findById("957000001"))
                .thenReturn(Mono.just(booking));

        // When
        Booking result = bookingService.getBooking("957000001").block();

        // Then
        assertNotNull(result);
        assertEquals("957000001", result.getBookingRef());
        assertEquals(ContainerType.DRY, result.getContainerType());
    }

    @Test
    void getBooking_whenNotExists_returnsEmpty() {
        // Given
        when(bookingRepository.findById("999999999"))
                .thenReturn(Mono.empty());

        // When
        Booking result = bookingService.getBooking("999999999").block();

        // Then
        assertNull(result);
    }
}
