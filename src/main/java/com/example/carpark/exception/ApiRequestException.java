package com.example.carpark.exception;

import lombok.Getter;

@Getter
public class ApiRequestException extends RuntimeException{
    private String message;
    private String missingProperty;

    public ApiRequestException(String missingProperty) {
        this.missingProperty = missingProperty;
        this.message = String.format("Your request misses properties: %s", missingProperty);
    }
}
