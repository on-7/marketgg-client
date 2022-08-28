package com.nhnacademy.marketgg.client.dto.request;

import com.nhnacademy.marketgg.client.jwt.ShaPasswordEncoder;
import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginRequest {

    @Email(message = "이메일 양식을 지켜주세요.")
    @NotBlank(message = "이메일을 입력해 주세요.")
    private final String email;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private final String password;

    public LoginRequest(String email,
                        @NotBlank(message = "비밀번호를 입력해 주세요.")
                        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{10,20}$",
                            message = "비밀번호는 영문과 특수문자 숫자를 포함하며 10자 이상이어야 합니다.")
                        String password) {

        this.email = email;
        if (Objects.nonNull(password)) {
            password = new ShaPasswordEncoder().encode(password);
        }
        this.password = password;
    }

}
