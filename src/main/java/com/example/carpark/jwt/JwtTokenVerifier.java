package com.example.carpark.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

@AllArgsConstructor
public class JwtTokenVerifier extends OncePerRequestFilter {
    private final JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("\nDoing filter internal ...");

        if (request.getServletPath().equals("/") ||
                request.getServletPath().startsWith("/login")) {
            System.out.println("request.getServletPath() = " + request.getServletPath());
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(jwtConfig.getTokenPrefix().length());

        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecretKey().getBytes());
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);

            String username = decodedJWT.getSubject();
            String[] stringOfAuthorities = decodedJWT.getClaim("authorities").asArray(String.class);

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            stream(stringOfAuthorities).forEach(
                    stringOfAuthority -> {
                        authorities.add(new SimpleGrantedAuthority(stringOfAuthority));
                    }
            );

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    authorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            System.out.println("Something is wrong when doing internal filter: " + e.getMessage());

            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("error", e.getMessage());
            response.setContentType("application/json");
            response.setStatus(SC_FORBIDDEN);

            new ObjectMapper().writeValue(
                    response.getOutputStream(),
                    responseMap
            );

        } finally {
            filterChain.doFilter(request, response);
        }

    }
}
