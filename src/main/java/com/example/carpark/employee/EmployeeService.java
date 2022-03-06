package com.example.carpark.employee;

import com.example.carpark.exception.NotFoundException;
import com.example.carpark.role.Role;
import com.example.carpark.role.RoleName;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeDao employeeDao;

    public List<Employee> getAllEmployees() {
        return employeeDao.findAll();
    }

    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeDao.findById(employeeId);
    }

    public Optional<Employee> getEmployeeByAccount(String account) {
        return employeeDao.findByAccount(account);
    }

    public Employee registerEmployee(Employee employee) throws SQLIntegrityConstraintViolationException {
        if (employee.getRoles() == null) {
            employee.setRoles(
                    Set.of(
                            new Role(RoleName.USER)
                    )
            );
        }
        return employeeDao.save(employee);
    }

    public Employee editEmployee(Long employeeId, Employee employee) {
        Optional<Employee> optionalEmployee = employeeDao.findById(employeeId);

        if (!optionalEmployee.isPresent()) {
            throw new NotFoundException("employee", "id", employeeId.toString());
        }

        employee.setEmployeeId(employeeId);
        return employeeDao.save(employee);
    }

    public void deleteEmployee(Long employeeId) throws EmptyResultDataAccessException {
        Optional<Employee> optionalEmployee = employeeDao.findById(employeeId);

        if (!optionalEmployee.isPresent()) {
            throw new NotFoundException("employee", "id", employeeId.toString());
        }

        employeeDao.deleteById(employeeId);
    }
}
