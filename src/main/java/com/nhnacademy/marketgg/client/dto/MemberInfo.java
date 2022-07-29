package com.nhnacademy.marketgg.client.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberInfo {

    private String email;
    private String name;
    private String phoneNumber;
    private String memberGrade;
    private Character gender;
    private LocalDate birthDay;
    private LocalDateTime ggpassUpdatedAt;

}
