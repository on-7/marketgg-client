package com.nhnacademy.marketgg.client.dto.payment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;

/**
 * 결제 요청에 대한 정보를 담고 있는 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@AllArgsConstructor
public class PaymentVerifyRequest {

    @NotBlank
    @Size(min = 2, max = 4)
    private final String paymentType;

    // @ApiParam(value = "결제금액", required = true)
    @NotNull
    private final Long amount;

    // @ApiParam(value = "Market GG 에서 사용하는 주문 고유번호", required = true)
    @NotBlank
    @Size(min = 6, max = 64)
    private final String orderId;

    // @ApiParam(value = "주문 명", example = "[KF365] 아보카도 200g (1걔) 외 2건", required = true)
    @NotBlank
    @Size(max = 100)
    private final String orderName;

    @NotBlank
    @Size(max = 100)
    private final String customerName;

    @NotBlank
    @Size(max = 100)
    private final String customerEmail;

}
