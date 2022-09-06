package com.nhnacademy.marketgg.client.dto.order;

import com.nhnacademy.marketgg.client.dto.coupon.UsedCouponResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 상세 조회시 필요한 정보를 전달하기 위한 DTO 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 * @since 1.0.0
 */
@NoArgsConstructor
@Getter
public class OrderDetailRetrieveResponse {

    private Long id;

    private Long memberId;

    private String memberName;

    private String orderName;

    private Long totalAmount;

    private String orderStatus;

    private UsedCouponResponse usedCoupon;

    private Integer usedPoint;

    private String trackingNo;

    private Integer zipCode;

    private String address;

    private String detailAddress;

    private LocalDateTime createdAt;

    private List<ProductToOrder> orderProductList;

    public void checkTrackingNo(final String trackingNo) {
        if (Objects.isNull(trackingNo)) {
            this.trackingNo = "";
        }
    }

}
