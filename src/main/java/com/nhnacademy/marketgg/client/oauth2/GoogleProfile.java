package com.nhnacademy.marketgg.client.oauth2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class GoogleProfile {

    private String email;
    private String name;
    private String provider;

}
