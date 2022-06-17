package com.example.HomeService_MVC.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
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

    @ExceptionHandler(SubServicesNotFoundException.class)
    public ResponseEntity<String> SubServicesExceptionHandler(SubServicesNotFoundException e) {
        LOGGER.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(InvalidProposedPriceException.class)
    public ResponseEntity<String> InvalidProposedPriceExceptionHandler(InvalidProposedPriceException e) {
        LOGGER.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> OrderExceptionHandler(OrderNotFoundException e) {
        LOGGER.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(OfferNotFoundException.class)
    public ResponseEntity<String> offerExceptionHandler(OfferNotFoundException e) {
        LOGGER.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NotEnoughBalanceException.class)
    public ResponseEntity<String> balanceExceptionHandler(NotEnoughBalanceException e) {
        LOGGER.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<String> dateExceptionHandler(InvalidDateException e) {
        LOGGER.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> dataIntegrityExceptionHandler(DataIntegrityViolationException e){
        LOGGER.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("شما قبلا یک پیشنهاد روی این سفارش ثبت کرده اید");
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> badRequestExceptionHandler(BadRequestException e){
        LOGGER.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> ForbiddenExceptionHandler(ForbiddenException e){
        LOGGER.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
}
