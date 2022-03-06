package com.example.carpark.car;

import com.example.carpark.car.dto.CarRequest;
import com.example.carpark.exception.AlreadyExistsException;
import com.example.carpark.exception.FKConstraintException;
import com.example.carpark.exception.LimitException;
import com.example.carpark.exception.NotFoundException;
import com.example.carpark.parking_lot.ParkingLot;
import com.example.carpark.parking_lot.ParkingLotDao;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarService {
    private final CarDao carDao;
    private final ParkingLotDao parkingLotDao;

    public List<Car> getAllCars() {
        return carDao.findAll();
    }

    public Car registerCar(CarRequest carRequest) {
        return registerAndEditCar("register", carRequest);
    }

    public Car editCar(CarRequest carRequest) {
        return registerAndEditCar("edit", carRequest);
    }

    public void deleteCar(String licensePlate) {
        try {
            carDao.deleteById(licensePlate);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("car", "license plate", licensePlate.toString());
        } catch (Exception e) {
            throw new FKConstraintException();
        }
    }

    @Transactional
    Car registerAndEditCar(String option, CarRequest carRequest) {
        Optional<ParkingLot> optionalParkingLot = parkingLotDao.findById(carRequest.getParkId());

        if (!optionalParkingLot.isPresent()) {
            throw new NotFoundException("parking lot", "id", carRequest.getParkId().toString());
        }

        switch (option) {
            case "register":
                if (!requestIsValidForRegistering(carRequest)) {
                    throw new AlreadyExistsException("car", "license plate", carRequest.getLicensePlate());
                }
                break;

            case "edit":
                if (requestIsValidForRegistering(carRequest)) {
                    throw new NotFoundException("car", "license plate", carRequest.getLicensePlate());
                }
                break;
        }

        ParkingLot parkingLot = optionalParkingLot.get();

        if (option.equals("register")) {
            //Update number of current cars in the parking lot
            if (!(parkingLot.getCurrentCarNumber() < parkingLot.getMaxCarNumber())) {
                throw new LimitException("car", "parking lot", parkingLot.getMaxCarNumber().toString());
            }

            parkingLot.setCurrentCarNumber(parkingLot.getCurrentCarNumber() + 1);
            parkingLotDao.save(parkingLot);
        }

        Car car = new Car(
                carRequest.getLicensePlate(),
                carRequest.getCarColor(),
                carRequest.getCarType(),
                parkingLot
        );

        return carDao.save(car);
    }

    private boolean requestIsValidForRegistering(CarRequest carRequest) {
        if (carDao.findById(carRequest.getLicensePlate()).orElse(null) != null) {
            return false;
        }
        return true;
    }
}
