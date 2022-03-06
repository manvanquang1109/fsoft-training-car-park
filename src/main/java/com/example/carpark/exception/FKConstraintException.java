package com.example.carpark.exception;

import lombok.Getter;

@Getter
public class FKConstraintException extends RuntimeException {
    private String message;

    public FKConstraintException() {
        this.message = "Cannot delete a parent row. Please check again.";
    }
}
