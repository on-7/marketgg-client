package com.nhnacademy.marketgg.client.dto.payment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 회원이 가상계좌에 주문한 건에 대해 입금한 정보를 포함하고 있는 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Getter
@ToString
public class VirtualAccountDepositRequest {

    // @NotNull
    // private final LocalDateTime createdAt;

    @NotBlank
    @Size(max = 50)
    private final String secret;

    @NotBlank
    @Size(max = 20)
    private final String status;

    // @NotBlank
    // @Size(max = 64)
    // private final String transactionKey;

    @NotBlank
    @Size(min = 6, max = 64)
    private final String orderId;

}
