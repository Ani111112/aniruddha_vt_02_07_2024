package com.example.UrlShorter.exception;

public class UrlDoesNotExitsException extends RuntimeException{
    public UrlDoesNotExitsException(String message) {
        super(message);
    }
}
