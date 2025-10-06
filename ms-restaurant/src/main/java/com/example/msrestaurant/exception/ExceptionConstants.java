package com.example.msrestaurant.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionConstants {
    UNEXPECTED_EXCEPTION("UNEXPECTED_EXCEPTION", "Unexpected exception occurred"),
    RESTAURANT_NOT_FOUND("RESTAURANT_NOT_FOUND", "Restaurant not found by id"),
    HTTP_METHOD_IS_NOT_CORRECT("HTTP_METHOD_IS_NOT_CORRECT", "http method is not correct");

    private final String code;
    private final String message;
}