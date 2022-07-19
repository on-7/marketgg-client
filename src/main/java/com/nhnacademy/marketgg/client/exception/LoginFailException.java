package com.nhnacademy.marketgg.client.exception;

public class LoginFailException extends IllegalArgumentException {

    public LoginFailException() {
        super("아이디 또는 비밀번호가 잘못되었습니다.");
    }

}
