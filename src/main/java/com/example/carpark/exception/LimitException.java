package com.example.carpark.exception;

import lombok.Getter;

@Getter
public class LimitException extends RuntimeException{
    private String message;
    private String entity;
    private String containingEntity;
    private String value;

    public LimitException(String message) {
        super(message);
        this.message = message;
    }

    public LimitException(String entity, String containingEntity, String value) {
        this.entity = entity;
        this.containingEntity = containingEntity;
        this.value = value;
        this.message = String.format("The number of %ss have reached the limit of %s: %s", entity, containingEntity, value);
    }
}
