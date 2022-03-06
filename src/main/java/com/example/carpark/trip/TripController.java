package com.example.carpark.trip;

import com.example.carpark.exception.FKConstraintException;
import com.example.carpark.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/trips")
@AllArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

    @PostMapping
    public ResponseEntity registerTrip(@RequestBody Trip trip) {
        return new ResponseEntity(tripService.registerTrip(trip), OK);
    }

    @PutMapping
    public ResponseEntity editTrip(@RequestBody Trip trip) {
        return new ResponseEntity(tripService.editTrip(trip), OK);
    }

    @DeleteMapping(path = "{tripId}")
    public ResponseEntity deleteTrip(@PathVariable Long tripId) {
            tripService.deleteTrip(tripId);
            return new ResponseEntity("Delete successfully", OK);

    }
}
