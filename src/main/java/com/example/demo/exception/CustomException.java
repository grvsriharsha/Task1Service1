package com.example.demo.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class CustomException extends RuntimeException {

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    private HttpStatus httpStatus;

    public CustomException(String mssg, Throwable t, HttpStatus httpStatus) {
        super(mssg, t);
        this.httpStatus=httpStatus;
    }


}