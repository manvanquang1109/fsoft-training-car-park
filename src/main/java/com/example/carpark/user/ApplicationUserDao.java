package com.example.carpark.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationUserDao {
    Optional<ApplicationUser> findApplicationUserByUsername(String username);
}
