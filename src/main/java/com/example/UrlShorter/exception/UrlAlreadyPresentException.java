package com.example.UrlShorter.exception;

public class UrlAlreadyPresentException extends RuntimeException{
    public UrlAlreadyPresentException(String message) {
        super(message);
    }
}
