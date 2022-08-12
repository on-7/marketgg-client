package com.nhnacademy.marketgg.client.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private LocalDateTime ggpassUpdatedAt;

    public boolean isNull() {
        return email == null && name == null && memberGrade == null;
    }

}
