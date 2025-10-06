package com.example.msmenu.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionConstants {
    UNEXPECTED_EXCEPTION("UNEXPECTED_EXCEPTION", "Unexpected exception occurred"),
    HTTP_METHOD_IS_NOT_CORRECT("HTTP_METHOD_IS_NOT_CORRECT", "http method is not correct"),
    MENU_NOT_FOUND("MENU_NOT_FOUND", "Menu not found by id"),
    RESTAURANT_NOT_FOUND("RESTAURANT_NOT_FOUND", "Restaurant not found by id");

    private final String code;
    private final String message;
}