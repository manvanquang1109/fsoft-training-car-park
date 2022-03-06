package com.example.carpark.booking_office;

import com.example.carpark.booking_office.dto.BookingOfficeRequest;
import com.example.carpark.exception.NotFoundException;
import com.example.carpark.trip.Trip;
import com.example.carpark.trip.TripDao;
import lombok.AllArgsConstructor;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingOfficeService {

    private final BookingOfficeDao bookingOfficeDao;
    private final TripDao tripDao;

    public List<BookingOffice> getAllBookingOffices() {
        return bookingOfficeDao.findAll();
    }

    public BookingOffice registerBookingOffice(BookingOfficeRequest bookingOfficeRequest)
            throws HttpMessageNotReadableException, IllegalArgumentException {

        Optional<Trip> optionalTrip = tripDao.findById(bookingOfficeRequest.getTripId());
        if (!optionalTrip.isPresent()) {
            throw new NotFoundException("trip", "id", bookingOfficeRequest.getTripId().toString());
        }

        if (!requestIsValid(bookingOfficeRequest)) {
            throw new IllegalStateException("End date must be after start date");
        }

        BookingOffice bookingOffice = new BookingOffice(
                bookingOfficeRequest.getOfficeName(),
                bookingOfficeRequest.getStartContractDeadline(),
                bookingOfficeRequest.getEndContractDeadline(),
                optionalTrip.get()
        );

        return bookingOfficeDao.save(bookingOffice);
    }

    public void deleteBookingOffice(Long officeId) {
        Optional<BookingOffice> optionalBookingOffice = bookingOfficeDao.findById(officeId);

        if (!optionalBookingOffice.isPresent()) {
            throw new NotFoundException("booking office", "id", officeId.toString());
        }

        bookingOfficeDao.deleteById(officeId);
    }

    private boolean requestIsValid(BookingOfficeRequest bookingOfficeRequest) {
        if (bookingOfficeRequest.getStartContractDeadline().isAfter(
                bookingOfficeRequest.getEndContractDeadline()
        )) {
            return false;
        }

        return true;
    }
}
