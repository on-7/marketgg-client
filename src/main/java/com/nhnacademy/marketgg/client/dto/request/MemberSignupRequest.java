package com.nhnacademy.marketgg.client.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class MemberSignupRequest {

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private Character gender;
    private Boolean isAdmin;
    private LocalDate birthDate;
    private String referrerEmail;
    private Integer zipcode;
    private String address;
    private String detailAddress;

    public MemberSignupToAuth getSignupRequestToAuth() {
        return new MemberSignupToAuth(this.email
                , this.password
                , this.name
                , this.phoneNumber
                , this.referrerEmail);
    }

    public MemberSignupToShopMember getSignupRequestToShopMember(String uuid, String referrerUuid) {
        return new MemberSignupToShopMember(this.gender
                , Boolean.FALSE
                , this.birthDate
                , uuid
                , referrerUuid
                , this.zipcode
                , this.address
                , this.detailAddress
        );
    }

}
