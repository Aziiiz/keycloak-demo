package com.aziz.springbootkeycloakdemo.common.exception;

import org.springframework.http.HttpStatus;

public class Exception extends RuntimeException {

    private ExceptionErrorCode exceptionErrorCode;
    private String errorMessage;
    private HttpStatus httpStatus;

    public Exception() {
        this.exceptionErrorCode = ExceptionErrorCode.INTERNAL_SERVER_ERROR;
        this.errorMessage = "";
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public Exception(ExceptionErrorCode exceptionErrorCode) {
        this.exceptionErrorCode = exceptionErrorCode;
        this.errorMessage = exceptionErrorCode.getMessage();
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public Exception(ExceptionErrorCode exceptionErrorCode, HttpStatus httpStatus) {
        this.exceptionErrorCode = exceptionErrorCode;
        this.errorMessage = exceptionErrorCode.getMessage();
        this.httpStatus = httpStatus;
    }

    // Getters and setters
    public ExceptionErrorCode getErrorCode() {
        return exceptionErrorCode;
    }

    public void setErrorCode(ExceptionErrorCode exceptionErrorCode) {
        this.exceptionErrorCode = exceptionErrorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
