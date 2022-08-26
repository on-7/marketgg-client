package com.nhnacademy.marketgg.client.exception.auth;

public class UnAuthorizationException extends RuntimeException {

    private static final String MESSAGE = "접근 할 수 없습니다.";

    public UnAuthorizationException() {
        super(MESSAGE);
    }

}
