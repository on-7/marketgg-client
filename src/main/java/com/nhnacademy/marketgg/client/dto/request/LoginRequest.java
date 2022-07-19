package com.nhnacademy.marketgg.client.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginRequest {

    @Email
    @NotBlank
    private final String email;

    @NotBlank
    private final String password;

}
