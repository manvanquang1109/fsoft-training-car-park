package com.example.carpark;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = {"","login"})
public class HomeController {

    @GetMapping
    public ResponseEntity getHome() {
        return new ResponseEntity("Welcome", OK);
    }

    @PostMapping
    public ResponseEntity postHome() {
        return new ResponseEntity("Welcome", OK);
    }
}
