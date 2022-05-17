package com.example.HomeService_MVC.controller;

import com.example.HomeService_MVC.controller.exception.ExpertNotFoundException;
import com.example.HomeService_MVC.controller.exception.ServicesNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ServicesNotFoundException.class)
    public ResponseEntity<String> serviceExceptionHandler(ServicesNotFoundException e) {
        LOGGER.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ExpertNotFoundException.class)
    public ResponseEntity<String> expertExceptionHandler(ExpertNotFoundException e) {
        LOGGER.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
