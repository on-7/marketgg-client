package com.nhnacademy.marketgg.client.dto.member;

import com.nhnacademy.marketgg.client.jwt.ShaPasswordEncoder;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberUpdateRequest {

    @NotBlank
    private final String password;
    @NotBlank
    private final String name;
    @NotBlank
    private final String phoneNumber;

    public MemberUpdateRequest(String password, String name, String phoneNumber) {
        this.password = new ShaPasswordEncoder().encode(password);
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

}
