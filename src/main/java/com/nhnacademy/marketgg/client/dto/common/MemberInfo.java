package com.nhnacademy.marketgg.client.dto.common;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
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
        return email == null || name == null || memberGrade == null;
    }

}
