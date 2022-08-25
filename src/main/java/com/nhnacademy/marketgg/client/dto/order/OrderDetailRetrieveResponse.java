package com.nhnacademy.marketgg.client.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    private String orderName;

    private Long totalAmount;

    private String orderStatus;

    private Integer usedPoint;

    private String trackingNo;

    private Integer zipCode;

    private String address;

    private String detailAddress;

    private LocalDateTime createdAt;

    private List<ProductToOrder> orderProductList;

}
