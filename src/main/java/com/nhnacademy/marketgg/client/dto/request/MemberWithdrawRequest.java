package com.nhnacademy.marketgg.client.dto.request;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@RequiredArgsConstructor
@Getter
public class MemberWithdrawRequest {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDateTime deletedAt;

}
