package com.example.HomeService_MVC.controller.exception;

public class InvalidProposedPriceException extends RuntimeException{

    public InvalidProposedPriceException(String message) {
        super(message);
    }
}
