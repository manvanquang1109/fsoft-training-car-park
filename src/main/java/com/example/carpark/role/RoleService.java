package com.example.carpark.role;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleDao roleDao;

    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }
}
