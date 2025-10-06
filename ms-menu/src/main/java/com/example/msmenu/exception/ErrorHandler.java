package com.example.msmenu.exception;

import com.example.msmenu.exception.custom.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.msmenu.exception.ExceptionConstants.HTTP_METHOD_IS_NOT_CORRECT;
import static com.example.msmenu.exception.ExceptionConstants.UNEXPECTED_EXCEPTION;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception exception) {
        log.error("Exception, ", exception);
        return new ErrorResponse(UNEXPECTED_EXCEPTION.getCode(), UNEXPECTED_EXCEPTION.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(METHOD_NOT_ALLOWED)
    public ErrorResponse handle(HttpRequestMethodNotSupportedException exception) {
        log.error("HttpRequestMethodNotSupportedException, ", exception);
        return new ErrorResponse(HTTP_METHOD_IS_NOT_CORRECT.getCode(), HTTP_METHOD_IS_NOT_CORRECT.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handle(NotFoundException exception) {
        log.error("NotFoundException, ", exception);
        return new ErrorResponse(exception.getCode(), exception.getMessage());
    }
}