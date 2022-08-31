package com.nhnacademy.marketgg.client.dto.coupon;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UsedCouponResponse {

    private String name;

    private Double discountAmount;

    private String type;

}
