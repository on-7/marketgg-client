package com.nhnacademy.marketgg.client.dto.coupon;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CouponRetrieveResponse {

    private Long id;

    private String name;

    private String type;

    private Integer expiredDate;

    private Integer minimumMoney;

    private Double discountAmount;

    private Boolean isActive;

}
