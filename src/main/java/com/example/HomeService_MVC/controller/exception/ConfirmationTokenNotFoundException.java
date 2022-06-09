package com.example.HomeService_MVC.controller.exception;

public class ConfirmationTokenNotFoundException extends RuntimeException{

    public ConfirmationTokenNotFoundException(String message) {
        super(message);
    }
}
