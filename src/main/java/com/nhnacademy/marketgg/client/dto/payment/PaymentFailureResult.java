package com.nhnacademy.marketgg.client.dto.payment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 결제 실패 정보를 포함한 객체입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public class PaymentFailureResult {

    @NotBlank
    @Size(min = 1, max = 255)
    private final String code;

    @NotBlank
    @Size(max = 200)
    private final String message;

    @NotBlank
    @Size(max = 20)
    private final String orderId;

    public static PaymentFailureResult create(String code, String message, String orderId) {
        return new PaymentFailureResult(code, message, orderId);
    }

}
