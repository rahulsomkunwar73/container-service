package com.msk.containerservice.repository;

import com.msk.containerservice.model.Booking;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookingRepository extends ReactiveMongoRepository<Booking, String> {
}
