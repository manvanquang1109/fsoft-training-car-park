package com.example.carpark.booking_office;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingOfficeDao extends JpaRepository<BookingOffice, Long> {
}
