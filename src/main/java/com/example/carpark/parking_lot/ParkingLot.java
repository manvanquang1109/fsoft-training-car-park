package com.example.carpark.parking_lot;

import com.example.carpark.car.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ParkingLot {
    @Id
    @SequenceGenerator(
            name = "parking_lot_sequence",
            sequenceName = "parking_lot_sequence"
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "parking_lot_sequence"
    )
    private Long parkId;

    @Column(nullable = false)
    private String parkName;

    @Column(nullable = false)
    private Integer parkPrice;

    @Column(nullable = false)
    private Integer maxCarNumber;

    @Column(nullable = false)
    private Integer currentCarNumber;

    public ParkingLot(String parkName, Integer parkPrice, Integer maxCarNumber) {
        this.parkName = parkName;
        this.parkPrice = parkPrice;
        this.maxCarNumber = maxCarNumber;
        this.currentCarNumber = 0;
    }

    public ParkingLot(String parkName, Integer parkPrice, Integer maxCarNumber, Integer currentCarNumber) {
        this.parkName = parkName;
        this.parkPrice = parkPrice;
        this.maxCarNumber = maxCarNumber;
        this.currentCarNumber = currentCarNumber;
    }
}
