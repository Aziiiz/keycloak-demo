package com.aziz.springbootkeycloakdemo.common.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiControllerAdvice {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleApiException( Exception ex) {
        return new ResponseEntity<>(
                new ExceptionResponse(ex.getErrorCode().name(), ex.getErrorMessage()),
                ex.getHttpStatus()
        );
    }


    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<?> handleJwtException(HttpServletRequest request, JwtException ex) {
        return new ResponseEntity<>(
                new ExceptionResponse(ExceptionErrorCode.UNAUTHORIZED.name(), ExceptionErrorCode.UNAUTHORIZED.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

}
