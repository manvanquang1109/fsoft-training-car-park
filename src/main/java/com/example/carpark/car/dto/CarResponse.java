package com.example.carpark.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarResponse {
    private String licensePlate;
    private String carType;
    private String carColor;
}
