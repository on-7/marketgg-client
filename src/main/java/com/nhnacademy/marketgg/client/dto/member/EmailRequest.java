package com.nhnacademy.marketgg.client.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    private String email;
    private boolean isReferrer;

}
