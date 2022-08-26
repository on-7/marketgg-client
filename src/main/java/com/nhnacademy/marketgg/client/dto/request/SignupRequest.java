package com.nhnacademy.marketgg.client.dto.request;

import com.nhnacademy.marketgg.client.jwt.ShaPasswordEncoder;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@RequiredArgsConstructor
@Getter
public class SignupRequest {

    @Email
    @NotBlank
    private final String email;

    @NotBlank
    private String password;

    @NotBlank
    private final String name;

    @NotBlank
    private final String phoneNumber;

    @NotNull
    private final Character gender;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate birthDate;

    private final String referrerEmail;

    @NotNull
    private final Integer zipcode;

    @NotBlank
    private final String address;

    @NotBlank
    private final String detailAddress;

    @NotNull
    private final Boolean inlineRadioOptions;

    private final String provider;

    public SignupRequest(String email, String password, String name, String phoneNumber, Character gender,
                         LocalDate birthDate, String referrerEmail, Integer zipcode, String address,
                         String detailAddress,
                         Boolean inlineRadioOptions, String provider) {

        this.email = email;
        this.password = new ShaPasswordEncoder().encode(password);
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
