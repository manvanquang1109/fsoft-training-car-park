package com.example.carpark.ticket;

import com.example.carpark.car.Car;
import com.example.carpark.car.CarDao;
import com.example.carpark.exception.LimitException;
import com.example.carpark.exception.NotFoundException;
import com.example.carpark.ticket.dto.TicketRequest;
import com.example.carpark.trip.Trip;
import com.example.carpark.trip.TripDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TicketService {

    private final TicketDao ticketDao;
    private final CarDao carDao;
    private final TripDao tripDao;

    public List<Ticket> getAllTickets() {
        return ticketDao.findAll();
    }

    public Optional<Ticket> getTicketById(Long ticketId) {
        return ticketDao.findById(ticketId);
    }

    @Transactional
    public Ticket registerTicket(TicketRequest ticketRequest) {

        String licensePlate = ticketRequest.getLicensePlate();
        Long tripId = ticketRequest.getTripId();

        Optional<Car> optionalCar = carDao.findById(licensePlate);
        Optional<Trip> optionalTrip = tripDao.findById(tripId);

        if (!optionalCar.isPresent()) {
            throw new NotFoundException("car", "license plate", licensePlate);
        }

        if (!optionalTrip.isPresent()) {
            throw new NotFoundException("trip", "id", tripId.toString());
        }

        Trip trip = optionalTrip.get();
        if (!(trip.getBookedTicketNumber() < trip.getMaximumOnlineTicketNumber())) {
            throw new LimitException("booked ticket", "trip", trip.getMaximumOnlineTicketNumber().toString());
        }

        trip.setBookedTicketNumber(trip.getBookedTicketNumber() + 1);

        Ticket ticket = new Ticket(
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                ticketRequest.getCustomerName(),
                optionalCar.get(),
                optionalTrip.get()
        );

        tripDao.save(trip);
        return ticketDao.save(ticket);
    }

    public void deleteTicket(Long ticketId) {
        ticketDao.deleteById(ticketId);
    }
}
