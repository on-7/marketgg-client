package com.nhnacademy.marketgg.client.jwt;

import com.nhnacademy.marketgg.client.exception.ServerException;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class JwtInfo implements Serializable {

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
