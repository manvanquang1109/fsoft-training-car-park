package com.example.carpark.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketRequest {
    private String customerName;
    private String licensePlate;
    private Long tripId;
}
