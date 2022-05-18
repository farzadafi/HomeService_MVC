package com.example.HomeService_MVC.controller.exception;

public class NotEnoughBalanceException extends RuntimeException{

    public NotEnoughBalanceException(String message) {
        super(message);
    }
}
