package com.example.carpark.exception;

import lombok.Getter;

@Getter
public class AlreadyExistsException extends RuntimeException{
    private String message;
    private String entity;
    private String column;
    private String value;

    public AlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

    public AlreadyExistsException(String entity, String column, String value) {
        this.entity = entity;
        this.column = column;
        this.value = value;
        this.message = String.format("Already had %s with %s: %s", entity, column, value);
    }
}
