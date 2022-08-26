package com.nhnacademy.marketgg.client.dto.request;

import com.nhnacademy.marketgg.client.jwt.ShaPasswordEncoder;
import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {

    @Email
    @NotBlank
    private final String email;

    @NotBlank
    private final String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        if (Objects.nonNull(password)) {
            password = new ShaPasswordEncoder().encode(password);
        }
        this.password = password;
    }

}
