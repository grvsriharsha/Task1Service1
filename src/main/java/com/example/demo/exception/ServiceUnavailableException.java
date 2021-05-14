package com.example.demo.exception;

public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException(String mssg)
    {
        super(mssg);
    }

    public ServiceUnavailableException(String mssg,Throwable t)
    {
        super(mssg,t);
    }
}
