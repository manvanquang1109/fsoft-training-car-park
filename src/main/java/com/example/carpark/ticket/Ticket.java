package com.example.carpark.ticket;

import com.example.carpark.car.Car;
import com.example.carpark.trip.Trip;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ticket {
    @Id
    @SequenceGenerator(
            name = "ticket_sequence",
            sequenceName = "ticket_sequence"
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "ticket_sequence"
    )
    private Long ticketId;

    @Column(nullable = false)
    private LocalDateTime bookingTime;

    @Column(nullable = false)
    private String customerName;

    @ManyToOne
    private Car car;

    @ManyToOne
    private Trip trip;

    public Ticket(LocalDateTime bookingTime, String customerName, Car car, Trip trip) {
        this.bookingTime = bookingTime;
        this.customerName = customerName;
        this.car = car;
        this.trip = trip;
    }
}
