package com.example.carpark.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarRequest {
    private String licensePlate;

    private String carType;
    private String carColor;

    private Long parkId;
}
