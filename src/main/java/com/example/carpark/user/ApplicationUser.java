package com.example.carpark.user;

import com.example.carpark.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@ToString
public class ApplicationUser implements UserDetails {

    private String username;
    private String password;
    private Set<? extends GrantedAuthority> authorities;

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    public ApplicationUser(Set<? extends GrantedAuthority> authorities, String username, String password) {

        this.authorities = authorities;
        this.username = username;
        this.password = password;
    }

    public ApplicationUser(Employee employee) {
        this.username = employee.getAccount();
        this.password = employee.getPassword();
        this.authorities = employee.getRoles()
                .stream()
                .map(
                        role -> new SimpleGrantedAuthority(role.getRoleName().name())
                )
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
