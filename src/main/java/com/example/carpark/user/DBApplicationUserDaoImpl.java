package com.example.carpark.user;

import com.example.carpark.employee.Employee;
import com.example.carpark.employee.EmployeeDao;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("database-application-user-dao-impl")
@AllArgsConstructor
public class DBApplicationUserDaoImpl implements ApplicationUserDao {

    private final EmployeeDao employeeDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> findApplicationUserByUsername(String username) {
        Optional<Employee> optionalEmployee = employeeDao.findByAccount(username);

        if (!optionalEmployee.isPresent()) {
            System.out.println("can't find employee from the DB with the username: " + username);
            return Optional.empty();
        }

        Employee employee = optionalEmployee.get();
        System.out.println(employee);
        employee.setPassword(
                passwordEncoder.encode(employee.getPassword())
        );

        Optional<ApplicationUser> optionalApplicationUser = Optional.of(new ApplicationUser(
                employee
        ));
        return optionalApplicationUser;
    }

}
