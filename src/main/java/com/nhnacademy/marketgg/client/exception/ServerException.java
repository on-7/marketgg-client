package com.nhnacademy.marketgg.client.exception;

public class ServerException extends RuntimeException {

    private static final String ERROR = "서버 에러 발생";
    public ServerException() {
        super(ERROR);
    }

}
