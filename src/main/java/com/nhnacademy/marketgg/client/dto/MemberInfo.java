package com.nhnacademy.marketgg.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@ToString
public class MemberInfo {

    private String email;
    private String name;
    private String phoneNumber;
    private String memberGrade;
    private Character gender;
    private LocalDate birthDay;

    public boolean isNull() {
        return email == null && name == null && memberGrade == null;
    }

}
