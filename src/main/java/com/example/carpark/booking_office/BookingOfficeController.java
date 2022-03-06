package com.example.carpark.booking_office;

import com.example.carpark.booking_office.dto.BookingOfficeRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/booking-offices")
@AllArgsConstructor
public class BookingOfficeController {

    private final BookingOfficeService bookingOfficeService;

    @GetMapping
    public ResponseEntity getAllBookingOffices() {
        return new ResponseEntity(bookingOfficeService.getAllBookingOffices(), OK);
    }

    @PostMapping
    public ResponseEntity registerBookingOffice(@RequestBody BookingOfficeRequest bookingOfficeRequest)
            throws HttpMessageNotReadableException, IllegalArgumentException {
        return new ResponseEntity(bookingOfficeService.registerBookingOffice(bookingOfficeRequest), OK);
    }

    @DeleteMapping(path = "{officeId}")
    public ResponseEntity deleteBookingOffice(@PathVariable Long officeId) {
        bookingOfficeService.deleteBookingOffice(officeId);
        return new ResponseEntity("Delete successfully", OK);
    }
}
