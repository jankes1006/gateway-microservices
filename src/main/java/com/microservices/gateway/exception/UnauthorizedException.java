package com.microservices.gateway.exception;

import com.microservices.gateway.enumerations.ExceptionType;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends WebException {

    public UnauthorizedException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public UnauthorizedException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public UnauthorizedException(HttpStatus httpStatus, String message, String errorDescription) {
        super(httpStatus, message, errorDescription);
    }

    public UnauthorizedException(HttpStatus httpStatus, String message, String errorDescription, ExceptionType exceptionType) {
        super(httpStatus, message, errorDescription, exceptionType);
    }

}
