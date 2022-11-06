package com.microservices.gateway.exception;

import com.microservices.gateway.enumerations.ExceptionType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WebException extends RuntimeException {

    private HttpStatus httpStatus;
    private String errorDescription;
    private ExceptionType exceptionType;

    public WebException(HttpStatus httpStatus) {
        this(httpStatus, null);
    }

    public WebException(HttpStatus httpStatus, String message) {
        this(httpStatus, message, null);
    }

    protected WebException(HttpStatus httpStatus, String message, String errorDescription) {
        this(httpStatus, message, errorDescription, ExceptionType.REGULAR);
    }

    protected WebException(HttpStatus httpStatus, String message, String errorDescription, ExceptionType exceptionType) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorDescription = errorDescription;
        this.exceptionType = exceptionType;
    }
}
