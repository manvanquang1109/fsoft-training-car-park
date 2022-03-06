package com.example.carpark.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private String message;
    private String entity;
    private String column;
    private String value;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String entity, String column, String value) {
        this.entity = entity;
        this.column = column;
        this.message = String.format("No %s found with %s: %s", entity, column, value);
    }
}
