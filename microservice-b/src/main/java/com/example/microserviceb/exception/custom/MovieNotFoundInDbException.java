package com.example.microserviceb.exception.custom;

public class MovieNotFoundInDbException extends RuntimeException {
    public MovieNotFoundInDbException(String message) {
        super(message);
    }
}
