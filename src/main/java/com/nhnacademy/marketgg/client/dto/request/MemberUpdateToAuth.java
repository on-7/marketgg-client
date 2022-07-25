package com.nhnacademy.marketgg.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberUpdateToAuth {

    private final String password;
    private final String name;
    private final String phoneNumber;

}
