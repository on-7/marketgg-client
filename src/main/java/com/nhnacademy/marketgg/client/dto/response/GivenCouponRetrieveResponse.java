package com.nhnacademy.marketgg.client.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GivenCouponRetrieveResponse {

    private Long couponId;

    private Long memberId;

    private String name;

    private String type;

    private Integer minimumMoney;

    private Double discountAmount;

    private LocalDateTime expiredDate;

    private String state;

}
