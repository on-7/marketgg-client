package com.nhnacademy.marketgg.client.dto.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CouponRequest {

    private String name;

    private String type;

    private Integer expiredDate;

    private Integer minimumMoney;

    private Double discountAmount;

}
