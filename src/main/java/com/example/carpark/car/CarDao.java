package com.example.carpark.car;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarDao extends JpaRepository<Car, String> {
}
