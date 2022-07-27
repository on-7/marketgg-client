package com.nhnacademy.marketgg.client.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class MemberWithdrawRequest {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDateTime deletedAt;

}
