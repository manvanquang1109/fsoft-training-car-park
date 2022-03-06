package com.example.carpark.security;

import com.example.carpark.jwt.JwtConfig;
import com.example.carpark.jwt.JwtTokenVerifier;
import com.example.carpark.jwt.JwtUsernamePasswordAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

//    private final PasswordEncoder passwordEncoder;
//
//    @Qualifier("database")
//    private final UserDetailsService userDetailsService;
    private JwtConfig jwtConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("Configuring");
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS).and()
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(super.authenticationManager(),  jwtConfig))
                .addFilterAfter(new JwtTokenVerifier(jwtConfig), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(POST, "/api/v1/tickets/**")
                .hasAnyAuthority("ADMIN_1", "USER")
                .antMatchers(
                        "/api/v1/roles/**",
                        "/api/v1/employees/**"
                ).hasAuthority("ADMIN_1")
                .antMatchers(
                        "/api/v1/parking-lots/**",
                        "/api/v1/cars/**",
                        "/api/v1/trips/**",
                        "/api/v1/tickets/**",
                        "/api/v1/booking-offices/**"
                ).hasAuthority("ADMIN_2")
                .antMatchers("/", "/login/**").permitAll()
                .anyRequest().authenticated();
//                .formLogin()
//                .defaultSuccessUrl("/api/v1/employees", true)
//                .and()

    }

//    @Bean
//    AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//
//        return daoAuthenticationProvider;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }


//    //In memory username & password
//    @Override
//    @Bean("in-memory")
//    protected UserDetailsService userDetailsService() {
//        UserDetails annaUserDetails = User.builder()
//                .username("anna")
//                .password(passwordEncoder.encode("1"))
//                .authorities(Set.of(
//                        new SimpleGrantedAuthority("ADMIN_1")
//                ))
//                .build();
//
//        UserDetails lindaUserDetails = User.builder()
//                .username("linda")
//                .password(passwordEncoder.encode("1"))
//                .authorities(Set.of(
//                        new SimpleGrantedAuthority("ADMIN_2")
//                ))
//                .build();
//
//        return new InMemoryUserDetailsManager(Arrays.asList(annaUserDetails, lindaUserDetails));
//    }
}
