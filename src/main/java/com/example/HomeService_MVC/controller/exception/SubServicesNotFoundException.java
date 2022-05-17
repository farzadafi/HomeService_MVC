package com.example.HomeService_MVC.controller.exception;

public class SubServicesNotFoundException extends RuntimeException{

    public SubServicesNotFoundException(String message) {
        super(message);
    }
}
