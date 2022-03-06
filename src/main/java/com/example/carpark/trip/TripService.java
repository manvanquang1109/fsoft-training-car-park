package com.example.carpark.trip;

import com.example.carpark.exception.AlreadyExistsException;
import com.example.carpark.exception.ApiRequestException;
import com.example.carpark.exception.FKConstraintException;
import com.example.carpark.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TripService {

    private final TripDao tripDao;

    List<Trip> getAllTrips() {
        return tripDao.findAll();
    }

    public Trip registerTrip(Trip trip) {
        if (trip.getTripId() != null) {
            Optional<Trip> optionalTrip = tripDao.findById(trip.getTripId());

            if (optionalTrip.isPresent()) {
                throw new AlreadyExistsException("trip", "id", trip.getTripId().toString());
            }
        }

        return tripDao.save(trip);
    }

    public Trip editTrip(Trip trip) {
        Optional<Trip> optionalTrip = null;
        try {
            optionalTrip = tripDao.findById(trip.getTripId());
        } catch (Exception e) {
            throw new ApiRequestException("id");
        }

        if (!optionalTrip.isPresent()) {
            throw new NotFoundException("trip", "id", trip.getTripId().toString());
        }

        return tripDao.save(trip);
    }

    public void deleteTrip(Long tripId) {
        try {
            tripDao.deleteById(tripId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("trip", "id", tripId.toString());
        } catch (Exception e) {
            throw new FKConstraintException();
        }
    }
}
