package com.nhnacademy.marketgg.client.exception.auth;

public class UnAuthenticException extends IllegalAccessException {

    private static final String MESSAGE = "로그인 후 접근 가능합니다.";

    public UnAuthenticException() {
        super(MESSAGE);
    }

}
