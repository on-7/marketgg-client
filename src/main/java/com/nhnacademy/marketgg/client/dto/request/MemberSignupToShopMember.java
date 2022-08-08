package com.nhnacademy.marketgg.client.dto.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberSignupToShopMember {

    private Character gender;
    private Boolean isAdmin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String uuid;
    private String referrerUuid;
    private Integer zipcode;
    private String address;
    private String detailAddress;

}
