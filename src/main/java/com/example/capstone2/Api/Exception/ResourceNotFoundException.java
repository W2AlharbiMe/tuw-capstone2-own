package com.example.capstone2.Api.Exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message + " not found.");
    }
}
