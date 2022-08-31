package com.nhnacademy.marketgg.client.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AuthResponse {

    private String email;
    private String name;
    private String phoneNumber;

}
