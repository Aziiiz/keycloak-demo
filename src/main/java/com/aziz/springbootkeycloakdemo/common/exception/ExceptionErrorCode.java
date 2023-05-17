package com.aziz.springbootkeycloakdemo.common.exception;

public enum ExceptionErrorCode {
    INTERNAL_SERVER_ERROR("Internal server error happened."),
    UNSUPPORTED_HTTP_METHOD("Unsupported HTTP method."),
    UNAUTHORIZED("Failed to authenticate since the JWT was invalid"),
    INSUFFICIENT_USER_ROLE("You do not have the necessary roles to access this resource");


    private final String message;

    ExceptionErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }


}
