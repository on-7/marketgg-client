package com.nhnacademy.marketgg.client.exception;

import org.springframework.http.HttpStatus;

public class LogoutException extends RuntimeException {

    private static final String LOGOUT_FAIL_MESSAGE = "로그아웃 실패!";

    public LogoutException(HttpStatus httpStatus) {
        super("[" + httpStatus + " " + LOGOUT_FAIL_MESSAGE);
    }
}
