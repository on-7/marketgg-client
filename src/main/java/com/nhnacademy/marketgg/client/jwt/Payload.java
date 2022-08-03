package com.nhnacademy.marketgg.client.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Payload {

    @JsonProperty("sub")
    private String sub;

    @JsonProperty("AUTHORITIES")
    private List<String> authorities;

    @JsonProperty("iat")
    private Long iat;

    @JsonProperty("exp")
    private Long exp;

}
