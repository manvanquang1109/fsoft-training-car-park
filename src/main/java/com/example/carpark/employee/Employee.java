package com.example.carpark.employee;


import com.example.carpark.role.Role;
import com.example.carpark.role.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long employeeId;

    @Column(unique = true, nullable = false)
    private String account;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = EAGER)
    private Set<Role> roles;

    private String department;
    private String sex;
    private LocalDate employeeBirthday;

    public Employee(Long employeeId, String account, String password) {
        this.employeeId = employeeId;
        this.account = account;
        this.password = password;

        this.roles = Set.of(
                new Role(
                        RoleName.USER
                )
        );
    }

    public Employee(Long employeeId, String account, String password, Set<Role> roles) {
        this.employeeId = employeeId;
        this.account = account;
        this.password = password;
        this.roles = roles;
    }

    public Employee(String account, String password) {
        this.account = account;
        this.password = password;
        this.roles = Set.of(
                new Role(
                        RoleName.USER
                )
        );
    }

    public Employee(String account, String password, Set<Role> roles) {
        this.account = account;
        this.password = password;
        this.roles = roles;
    }
}
