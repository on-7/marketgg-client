package com.nhnacademy.marketgg.client.jwt;

import com.nhnacademy.marketgg.client.exception.ServerException;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class JwtInfo implements Serializable {

    public static final String JWT_REDIS_KEY = "JWT";
    public static final String JWT_EXPIRE = "JWT-Expire";
    public static final String SESSION_ID = "SESSION-ID";

    private final String jwt;
    private final LocalDateTime jwtExpireDate;

    public JwtInfo(String jwt, String jwtExpireDate) {
        this.jwt = jwt;
        try {
            this.jwtExpireDate = LocalDateTime.parse(jwtExpireDate);
        } catch (Exception e) {
            throw new ServerException();
        }
    }

}
