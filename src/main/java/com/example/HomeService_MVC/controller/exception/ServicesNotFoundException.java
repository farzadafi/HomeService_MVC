package com.example.HomeService_MVC.controller.exception;

public class ServicesNotFoundException extends RuntimeException{

    public ServicesNotFoundException(String message) {
        super(message);
    }
}
