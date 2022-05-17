package com.example.HomeService_MVC.controller.exception;

public class OfferNotFoundException extends RuntimeException{

    public OfferNotFoundException(String message) {
        super(message);
    }
}
