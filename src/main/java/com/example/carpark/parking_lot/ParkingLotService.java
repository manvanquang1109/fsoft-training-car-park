package com.example.carpark.parking_lot;

import com.example.carpark.car.dto.CarResponse;
import com.example.carpark.exception.AlreadyExistsException;
import com.example.carpark.exception.ApiRequestException;
import com.example.carpark.exception.FKConstraintException;
import com.example.carpark.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ParkingLotService {
    private final ParkingLotDao parkingLotDao;

    public List<ParkingLot> getAllParkingLots() {
        return parkingLotDao.findAll();
    }

    public ParkingLot getParkingLotByName(String parkName) {
        return parkingLotDao.findByParkName(parkName);
    }

    public List<ParkingLot> getAvailableParkingLots() {
        return parkingLotDao.findAvailableParkingLots();
    }

    public List<CarResponse> getAllCarsInAParkingLot(Long parkId) {
        return parkingLotDao.findAllCarsInAParkingLot(parkId);
    }

    public ParkingLot registerParkingLot(ParkingLot parkingLot) {
        if (parkingLot.getParkId() != null) {
            Optional<ParkingLot> optionalParkingLot = parkingLotDao.findById(parkingLot.getParkId());

            if (optionalParkingLot.isPresent()) {
                throw new AlreadyExistsException("parking lot", "id", parkingLot.getParkId().toString());
            }
        }

        if (parkingLot.getCurrentCarNumber() == null) {
            parkingLot.setCurrentCarNumber(0);
        }

        return parkingLotDao.save(parkingLot);
    }

    public ParkingLot editParkingLot(ParkingLot parkingLot) {
        Optional<ParkingLot> optionalParkingLot = null;
        try {
            optionalParkingLot = parkingLotDao.findById(parkingLot.getParkId());
        } catch (Exception e) {
            throw new ApiRequestException("id");
        }

        if (!optionalParkingLot.isPresent()) {
            throw new NotFoundException("parking lot", "id", parkingLot.getParkId().toString());
        }

        if (parkingLot.getCurrentCarNumber() == null) {
            parkingLot.setCurrentCarNumber(optionalParkingLot.get().getCurrentCarNumber());
        }
        return parkingLotDao.save(parkingLot);
    }

    public void deleteParkingLot(Long parkId) {
        try {
            parkingLotDao.deleteById(parkId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("parking lot", "id", parkId.toString());
        } catch (Exception e) {
            throw new FKConstraintException();
        }
    }

}
