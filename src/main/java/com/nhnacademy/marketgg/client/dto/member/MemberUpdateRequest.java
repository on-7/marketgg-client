package com.nhnacademy.marketgg.client.dto.member;

import com.nhnacademy.marketgg.client.jwt.ShaPasswordEncoder;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class MemberUpdateRequest {

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private final String password;
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,10}$", message = "이름은 한글 또는 영어 2자에서 10자까지 입력 가능합니다.")
    @NotBlank
    private final String name;

    @Pattern(regexp = "^01([0|1|6|7|8|9])-?(\\d{3,4})-?(\\d{4})$", message = "휴대폰 번호를 확인 해주세요.")
    @NotBlank
    private final String phoneNumber;

    public MemberUpdateRequest(@NotBlank
                               @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{10,20}$",
                                   message = "비밀번호는 영문과 특수문자 숫자를 포함하며 10자 이상이어야 합니다.")
                               String password, String name, String phoneNumber) {
        if (Objects.nonNull(password)) {
            password = new ShaPasswordEncoder().encode(password);
        }
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

}
