package com.example.carpark.trip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Trip {

    @Id
    @SequenceGenerator(
            name = "trip_sequence",
            sequenceName = "trip_sequence"
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "trip_sequence"
    )
    private Long tripId;

    @Column(columnDefinition = "DATE")
    private LocalDate departureDate;

    @Column(columnDefinition = "TIME")
    private LocalTime departureTime;

    private Integer maximumOnlineTicketNumber;
    private Integer bookedTicketNumber = 0;

    public Trip(LocalDate departureDate, LocalTime departureTime, Integer maximumOnlineTicketNumber) {
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.maximumOnlineTicketNumber = maximumOnlineTicketNumber;
    }
}
