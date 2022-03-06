package com.example.carpark.car;

import com.example.carpark.parking_lot.ParkingLot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Car {
    @Id
    @Column(name = "license_plate")
    private String licensePlate;

    private String carType;
    private String carColor;

    @ManyToOne
    private ParkingLot parkingLot;
}
