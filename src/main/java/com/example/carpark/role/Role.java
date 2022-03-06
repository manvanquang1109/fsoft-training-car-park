package com.example.carpark.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    @Id
    @Enumerated(
            value = EnumType.STRING
    )
    private RoleName roleName;
}
