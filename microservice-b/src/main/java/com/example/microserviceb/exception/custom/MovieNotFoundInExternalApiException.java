package com.example.microserviceb.exception.custom;

public class MovieNotFoundInExternalApiException extends RuntimeException {
    public MovieNotFoundInExternalApiException (String message) {
        super(message);
    }
}
