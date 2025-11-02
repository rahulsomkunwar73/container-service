package com.msk.containerservice.repository;

import com.msk.containerservice.model.Booking;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Booking documents
 * Uses Spring Data Reactive MongoDB
 */
@Repository
public interface BookingRepository extends ReactiveMongoRepository<Booking, String> {
}
