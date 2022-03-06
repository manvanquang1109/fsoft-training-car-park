package com.example.carpark.ticket;

import com.example.carpark.ticket.dto.TicketRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/tickets")
@AllArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping(path = "{ticketId}")
    public Optional<Ticket> getTicketById(@PathVariable Long ticketId) {
        return ticketService.getTicketById(ticketId);
    }

    @PostMapping
    public ResponseEntity registerTicket(@RequestBody TicketRequest ticketRequest) {
        return new ResponseEntity(ticketService.registerTicket(ticketRequest), OK);
    }

    @DeleteMapping(path = "{ticketId}")
    public ResponseEntity deleteCar(@PathVariable Long ticketId) {
        ticketService.deleteTicket(ticketId);
        return new ResponseEntity("Delete successfully", OK);
    }
}
