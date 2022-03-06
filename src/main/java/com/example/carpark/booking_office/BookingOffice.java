package com.example.carpark.booking_office;

import com.example.carpark.trip.Trip;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookingOffice {
    @Id
    @SequenceGenerator(
            name = "booking_office_sequence",
            sequenceName = "booking_office_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "booking_office_sequence"
    )
    private Long officeId;
    private String officeName;
    private LocalDate startContractDeadline;
    private LocalDate endContractDeadline;

    @ManyToOne
    private Trip trip;

    public BookingOffice(String officeName, LocalDate startContractDeadline, LocalDate endContractDeadline) {
        this.officeName = officeName;
        this.startContractDeadline = startContractDeadline;
        this.endContractDeadline = endContractDeadline;
    }

    public BookingOffice(String officeName, LocalDate startContractDeadline, LocalDate endContractDeadline, Trip trip) {
        this.officeName = officeName;
        this.startContractDeadline = startContractDeadline;
        this.endContractDeadline = endContractDeadline;
        this.trip = trip;
    }
}
