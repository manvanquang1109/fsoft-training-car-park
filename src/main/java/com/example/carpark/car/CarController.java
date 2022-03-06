package com.example.carpark.car;

import com.example.carpark.car.dto.CarRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/cars")
@AllArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @PostMapping
    public ResponseEntity registerCar(@RequestBody CarRequest carRequest) {
        return new ResponseEntity(carService.registerCar(carRequest), OK);
    }

    @PutMapping
    public ResponseEntity editCar(@RequestBody CarRequest carRequest) {
        return new ResponseEntity(carService.editCar(carRequest), OK);
    }

    @DeleteMapping(path = "{licensePlate}")
    public void deleteCar(@PathVariable String licensePlate) {
        carService.deleteCar(licensePlate);
    }
}
