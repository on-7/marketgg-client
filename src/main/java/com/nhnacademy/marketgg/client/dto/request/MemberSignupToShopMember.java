package com.nhnacademy.marketgg.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberSignupToShopMember {

    private Character gender;
    private Boolean isAdmin;
    private LocalDate birthDate;
    private String uuid;
    private String referrerUuid;
    private Integer zipcode;
    private String address;
    private String detailAddress;

}
