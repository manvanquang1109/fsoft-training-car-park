package com.example.carpark.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.carpark.user.ApplicationUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@AllArgsConstructor
@Slf4j
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        try {
            System.out.println("\nAttempting authentication ...");

            UsernamePasswordAuthenticationRequest usernamePasswordAuthenticationRequest = new ObjectMapper().readValue(
                    request.getInputStream(),
                    UsernamePasswordAuthenticationRequest.class
            );
            System.out.println(usernamePasswordAuthenticationRequest);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    usernamePasswordAuthenticationRequest.getUsername(),
                    usernamePasswordAuthenticationRequest.getPassword()
            );


            Authentication authenticate = authenticationManager.authenticate(authentication);
            System.out.println(authenticate);
            return authenticate;

        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {

        System.out.println("\nAuthenticate successfully & creating token...");

        ApplicationUser user = (ApplicationUser) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret-key".getBytes());

        String token = JWT.create()
                .withSubject(user.getUsername())
                .withClaim("authorities",
                        user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtConfig.getExpirationTimeInMilSecond()))
                .sign(algorithm);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("Authorization", jwtConfig.getTokenPrefix() + token);
        response.setContentType("application/json");
        response.setStatus(SC_OK);

        new ObjectMapper().writeValue(
                response.getOutputStream(),
                responseMap
        );
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        logger.error("Failed authentication while attempting to access");

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("error", failed.getMessage());
        response.setContentType("application/json");
        response.setStatus(SC_FORBIDDEN);

        new ObjectMapper().writeValue(
                response.getOutputStream(),
                responseMap
        );
    }
}
