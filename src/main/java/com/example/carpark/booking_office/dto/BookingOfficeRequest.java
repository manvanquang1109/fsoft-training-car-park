package com.example.carpark.booking_office.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BookingOfficeRequest {
    private String officeName;
    private LocalDate startContractDeadline;
    private LocalDate endContractDeadline;

    private Long tripId;
}
