package com.example.carpark.trip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TripDao extends JpaRepository<Trip, Long> {

//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE Trip t SET t.bookedTicketNumber = (SELECT t.bookedTicketNumber + 1 FROM Trip t WHERE t.tripId = ?1) WHERE t.tripId = ?1")
//    int increaseBookedTicketNumber(Long tripId);
}
