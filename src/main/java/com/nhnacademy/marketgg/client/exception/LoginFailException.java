package com.nhnacademy.marketgg.client.exception;

public class LoginFailException extends IllegalArgumentException {

    private static final String LOGIN_FAIL_MESSAGE = "아이디 또는 비밀번호가 잘못되었습니다.";

    public LoginFailException() {
        super(LOGIN_FAIL_MESSAGE);
    }

}
