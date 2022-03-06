package com.example.carpark;

import com.example.carpark.booking_office.dto.BookingOfficeRequest;
import com.example.carpark.booking_office.BookingOfficeService;
import com.example.carpark.car.CarService;
import com.example.carpark.car.dto.CarRequest;
import com.example.carpark.employee.Employee;
import com.example.carpark.employee.EmployeeDao;
import com.example.carpark.parking_lot.ParkingLot;
import com.example.carpark.parking_lot.ParkingLotService;
import com.example.carpark.role.Role;
import com.example.carpark.role.RoleDao;
import com.example.carpark.role.RoleName;
import com.example.carpark.ticket.TicketService;
import com.example.carpark.ticket.dto.TicketRequest;
import com.example.carpark.trip.Trip;
import com.example.carpark.trip.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class CarParkApplication {

    private final RoleDao roleDao;
    private final EmployeeDao employeeDao;
    private final ParkingLotService parkingLotService;
    private final CarService carService;
    private final TripService tripService;
    private final TicketService ticketService;
    private final BookingOfficeService bookingOfficeService;

    private Trip trip1, trip2, trip3, trip4;
    private TicketRequest ticketRequest1, ticketRequest2, ticketRequest3;
    private BookingOfficeRequest bookingOfficeRequest1, bookingOfficeRequest2, bookingOfficeRequest3;

    public static void main(String[] args) {
        SpringApplication.run(CarParkApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            initValues();
            registerRolesAndEmployees();
            registerCarsAndParkingLots();
            registerTrips();
            registerTickets();
            registerBookingOffices();
        };
    }

    private void initValues() {
//        Trip
        trip1 = new Trip(
                LocalDate.of(2021, 11, 29),
                LocalTime.of(6, 30),
                16
        );

        trip2 = new Trip(
                LocalDate.of(2021, 12, 01),
                LocalTime.of(9, 0),
                3
        );

        trip3 = new Trip(
                LocalDate.of(2021, 12, 01),
                LocalTime.of(5, 45),
                16
        );

        trip4 = new Trip(
                LocalDate.of(2022, 01, 01),
                LocalTime.of(8, 00),
                20
        );

//        TicketRequest
        ticketRequest1 = new TicketRequest(
                "alan",
                "29A-1111",
                1L
        );

        ticketRequest2 = new TicketRequest(
                "blue",
                "29B-1111",
                1L
        );

        ticketRequest3 = new TicketRequest(
                "blue",
                "29A-1111",
                4L
        );

//        BookingOfficeRequest
        bookingOfficeRequest1 = new BookingOfficeRequest(
                "Fire Work",
                LocalDate.of(2021, 12, 14),
                LocalDate.of(2021, 12, 30),
                1L
        );

        bookingOfficeRequest2 = new BookingOfficeRequest(
                "Well Team",
                LocalDate.of(2022, 1, 3),
                LocalDate.of(2022, 1, 26),
                1L
        );

        bookingOfficeRequest3 = new BookingOfficeRequest(
                "Play More",
                LocalDate.of(2022, 1, 14),
                LocalDate.of(2022, 2, 26),
                1L
        );
    }

    private void registerRolesAndEmployees() {
        //REGISTER ROLES
        Role admin1Role = new Role(
                RoleName.ADMIN_1
        );

        Role admin2Role = new Role(
                RoleName.ADMIN_2
        );

        Role userRole = new Role(
                RoleName.USER
        );

        roleDao.save(admin1Role);
        roleDao.save(admin2Role);
        roleDao.save(userRole);

        //REGISTER EMPLOYEES
        employeeDao.save(
                new Employee(
                        "anna",
                        "1",
                        Set.of(
                                admin1Role,
                                admin2Role
                        )
                )
        );

        employeeDao.save(
                new Employee(
                        "ben",
                        "1",
                        Set.of(
                                admin1Role
                        )
                )
        );

        employeeDao.save(
                new Employee(
                        "carter",
                        "1",
                        Set.of(
                                admin2Role
                        )
                )
        );

        employeeDao.save(
                new Employee(
                        "dave",
                        "1"
                )
        );
    }

    private void registerCarsAndParkingLots() {
        //REGISTER PARKING LOTS
        parkingLotService.registerParkingLot(
                new ParkingLot(
                        "A",
                        120,
                        1500
                )
        );

        parkingLotService.registerParkingLot(
                new ParkingLot(
                        "B",
                        105,
                        1200
                )
        );

        parkingLotService.registerParkingLot(
                new ParkingLot(
                        "C",
                        110,
                        1050
                )
        );

        //REGISTER CARS
        carService.registerCar(
                new CarRequest(
                        "29A-1111",
                        "HUYNDAI",
                        "Silver",
                        1L
                )
        );

        carService.registerCar(
                new CarRequest(
                        "29B-1111",
                        "Honda",
                        "White",
                        2L
                )
        );

        carService.registerCar(
                new CarRequest(
                        "29C-1111",
                        "HUYNDAI",
                        "White",
                        1L
                )
        );
    }

    private void registerTrips() {
        tripService.registerTrip(trip1);
        tripService.registerTrip(trip2);
        tripService.registerTrip(trip3);
        tripService.registerTrip(trip4);
    }

    private void registerTickets() {
        ticketService.registerTicket(ticketRequest1);
        ticketService.registerTicket(ticketRequest2);
        ticketService.registerTicket(ticketRequest3);
    }

    private void registerBookingOffices() {
        bookingOfficeService.registerBookingOffice(bookingOfficeRequest1);
        bookingOfficeService.registerBookingOffice(bookingOfficeRequest2);
        bookingOfficeService.registerBookingOffice(bookingOfficeRequest3);
    }
}
