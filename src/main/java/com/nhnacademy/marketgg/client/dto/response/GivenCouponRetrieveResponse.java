package com.nhnacademy.marketgg.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class GivenCouponRetrieveResponse {

    private Long couponId;

    private Long memberId;

    private String name;

    private String type;

    private Integer minimumMoney;

    private Double discountAmount;

    private LocalDateTime expirationPeriod;

    private String state;

}
