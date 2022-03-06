package com.example.carpark.parking_lot;

import com.example.carpark.car.dto.CarResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLotDao extends JpaRepository<ParkingLot, Long> {
    ParkingLot findByParkName(String parkName);

    @Query("SELECT p FROM ParkingLot p WHERE p.currentCarNumber < p.maxCarNumber ORDER BY p.parkId")
    List<ParkingLot> findAvailableParkingLots();

    @Query("SELECT new com.example.carpark.car.dto.CarResponse(c.licensePlate, c.carType, c.carColor) " +
            "FROM Car c WHERE c.parkingLot.parkId = ?1")
    List<CarResponse> findAllCarsInAParkingLot(Long parkId);
}
