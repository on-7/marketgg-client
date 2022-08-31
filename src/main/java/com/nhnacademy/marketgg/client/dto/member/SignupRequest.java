package com.nhnacademy.marketgg.client.dto.member;

import com.nhnacademy.marketgg.client.jwt.ShaPasswordEncoder;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class SignupRequest {

    @Email
    @NotBlank(message = "이메일을 입력해 주세요.")
    private final String email;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private final String password;

    @Pattern(regexp = "^[가-힣a-zA-Z]{2,10}$", message = "이름은 한글 또는 영어 2자에서 10자까지 입력 가능합니다.")
    @NotBlank
    private final String name;

    @Pattern(regexp = "^01([0|1|6|7|8|9])-?(\\d{3,4})-?(\\d{4})$", message = "휴대폰 번호를 확인 해주세요.")
    @NotBlank
    private final String phoneNumber;

    @NotNull(message = "성별을 체크 해주세요.")
    private final Character gender;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate birthDate;

    private final String referrerEmail;

    @NotNull(message = "올바른 우편번호를 입력해 주세요.")
    private final Integer zipcode;

    @NotBlank(message = "올바른 주소를 입력해 주세요.")
    private final String address;

    @NotBlank(message = "올바른 상세주소를 입력해 주세요.")
    private final String detailAddress;

    @NotNull(message = "개인정보보호 동의를 체크해 주세요.")
    private final Boolean inlineRadioOptions;

    private final String provider;

    public SignupRequest(String email,
                         @NotBlank
                         @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{10,20}$",
                             message = "비밀번호는 영문과 특수문자 숫자를 포함하며 10자 이상이어야 합니다.")
                         String password,
                         String name, String phoneNumber,
                         Character gender,
                         LocalDate birthDate, String referrerEmail, Integer zipcode, String address,
                         String detailAddress, Boolean inlineRadioOptions, String provider) {

        this.email = email;
        if (Objects.nonNull(password)) {
            password = new ShaPasswordEncoder().encode(password);
        }
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDate = birthDate;
        this.referrerEmail = referrerEmail;
        this.zipcode = zipcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.inlineRadioOptions = inlineRadioOptions;
        this.provider = provider;
    }

}
