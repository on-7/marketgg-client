package com.nhnacademy.marketgg.client.dto.payment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 승인된 결제를 취소하기 위한 요청 객체입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public class PaymentCancelRequest {

    @NotBlank
    @Size(max = 20)
    private final String paymentKey;

    @NotBlank
    @Size(max = 200)
    private final String cancelReason;

    private final String bank;

    private final String accountNumber;

    private final String holderName;

}
