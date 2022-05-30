package com.example.HomeService_MVC.controller.exception;

public class InvalidDateException extends RuntimeException{

    public InvalidDateException(String message) {
        super(message);
    }
}
