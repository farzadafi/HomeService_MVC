package com.example.HomeService_MVC.controller.exception;

public class ExpertNotFoundException extends RuntimeException {

    public ExpertNotFoundException(String message) {
        super(message);
    }
}
