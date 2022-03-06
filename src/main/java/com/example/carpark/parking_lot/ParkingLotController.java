package com.example.carpark.parking_lot;

import com.example.carpark.car.dto.CarResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/parking-lots")
@AllArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @GetMapping
    public List<ParkingLot> getAllParkingLots() {
        return parkingLotService.getAllParkingLots();
    }

    @GetMapping("find-by-name")
    public ParkingLot getParkingLotByName(@RequestParam(name = "name") String parkName) {
        return parkingLotService.getParkingLotByName(parkName);
    }

    @GetMapping("available")
    public List<ParkingLot> getAvailableParkingLots() {
        return parkingLotService.getAvailableParkingLots();
    }

    @GetMapping("all-cars")
    public List<CarResponse> getAllCarsInAParkingLot(@RequestParam(name = "id") Long parkId) {
        return parkingLotService.getAllCarsInAParkingLot(parkId);
    }

    @PostMapping
    public ResponseEntity registerParkingLot(@RequestBody ParkingLot parkingLot) {
        return new ResponseEntity(parkingLotService.registerParkingLot(parkingLot), OK);
    }

    @PutMapping
    public ResponseEntity editParkingLot(@RequestBody ParkingLot parkingLot) {
        return new ResponseEntity(parkingLotService.editParkingLot(parkingLot), OK);
    }

    @DeleteMapping(path = "{parkId}")
    public ResponseEntity deleteParkingLot(@PathVariable Long parkId) {
        parkingLotService.deleteParkingLot(parkId);
        return new ResponseEntity("Delete successfully", OK);
    }

}
