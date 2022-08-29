package com.nhnacademy.marketgg.client.dto.order;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 주문 생성 요청에 대한 정보를 담고 있는 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public class OrderCreateRequest {

    @NotNull
    private final Long totalAmount;

    @NotNull
    private final Integer usedPoint;

    private Integer trackingNo;

    @NotBlank
    @Size(min = 2, max = 4)
    private final String paymentType;

    @NotBlank
    @Size(max = 100)
    private final String orderName;

    // @NotBlank
    @Size(max = 100)
    private final String customerName;

    // @NotBlank
    @Size(max = 100)
    private final String customerEmail;

}
