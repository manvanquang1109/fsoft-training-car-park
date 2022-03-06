package com.example.carpark.employee;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping(path = "find-by-id/{employeeId}")
    public ResponseEntity getEmployeeById(@PathVariable Long employeeId) {
        return new ResponseEntity(employeeService.getEmployeeById(employeeId), OK);
    }

    @GetMapping(path = "find-by-account/{account}")
    public ResponseEntity getEmployeeByAccount(@PathVariable String account) {
        return new ResponseEntity(employeeService.getEmployeeByAccount(account), OK);
    }

    @PostMapping
    public ResponseEntity registerEmployee(@RequestBody Employee parkingLot)
            throws SQLIntegrityConstraintViolationException {
        return new ResponseEntity(employeeService.registerEmployee(parkingLot), OK);
    }

    @PutMapping(path = "{employeeId}")
    public ResponseEntity editEmployee(
            @PathVariable Long employeeId,
            @RequestBody Employee employee) {
        return new ResponseEntity(employeeService.editEmployee(employeeId, employee), OK);
    }

    @DeleteMapping(path = "{employeeId}")
    public ResponseEntity deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity("Delete successfully", OK);
    }
}
