package com.example.carpark.user;

import com.example.carpark.employee.Employee;
import com.example.carpark.role.Role;
import com.example.carpark.role.RoleName;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository("fake")
@AllArgsConstructor
public class FakeApplicationDaoImpl implements ApplicationUserDao {
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> findApplicationUserByUsername(String username) {

        return getApplicationUsers().stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        ApplicationUser annaUser = new ApplicationUser(
                new Employee(
                        1L,
                        "anna",
                        passwordEncoder.encode("1"),
                        Set.of(
                                new Role(
                                        RoleName.ADMIN_1
                                )
                        )
                )
        );

        ApplicationUser lindaUser = new ApplicationUser(
                new Employee(
                        2L,
                        "linda",
                        passwordEncoder.encode("2"),
                        Set.of(
                                new Role(
                                        RoleName.ADMIN_2
                                )
                        )
                )
        );

        ApplicationUser tomUser = new ApplicationUser(
                new Employee(
                        3L,
                        "tom",
                        passwordEncoder.encode("3"),
                        Set.of(
                                new Role(
                                        RoleName.USER
                                )
                        )
                )
        );

        return Arrays.asList(
                annaUser,
                lindaUser,
                tomUser
        );
    }
}
