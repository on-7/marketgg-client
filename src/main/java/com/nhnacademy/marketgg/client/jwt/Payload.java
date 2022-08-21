package com.nhnacademy.marketgg.client.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * JWT 의  Payload 를 매핑시키기 위한 클래스입니다.
 *
 * @author 윤동열
 */
@NoArgsConstructor
@Getter
public class Payload implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;

    /**
     * JWT 발행인.
     */
    @JsonProperty("sub")
    private String sub;

    /**
     * 발행인의 권한 목록.
     */
    @JsonProperty("AUTHORITIES")
    private List<String> authorities;

    /**
     * JWT 발행일.
     */
    @JsonProperty("iat")
    private Long iat;

    /**
     * JWT 만료일.
     */
    @JsonProperty("exp")
    private Long exp;

}
