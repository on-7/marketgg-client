package com.nhnacademy.marketgg.client.dto.payment;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 결제 요청에 대해 결제대행사로부터 전달받는 응답 데이터 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class PaymentResponse implements Serializable {

    private static final long serialVersionUID = 1138662999082181099L;

    @NotBlank
    @Size(max = 200)
    private String paymentKey;

    @NotBlank
    @Size(min = 6, max = 64)
    private String orderId;

    @NotBlank
    @Size(max = 100)
    private String orderName;

    @NotBlank
    @Size(max = 20)
    private String status;

    @NotBlank
    @Size(max = 64)
    private String transactionKey;

    @NotNull
    private String requestedAt;

    @NotNull
    private String approvedAt;

    private String cashReceipt;

    @NotBlank
    @Size(max = 50)
    private String secret;

    @NotBlank
    @Size(min = 6, max = 8)
    private String type;

    private String easyPay;

    @NotBlank
    @Size(min = 2, max = 2)
    private String country;

    private String failure;

    private boolean isPartialCancelable;

    @NotBlank
    @Size(min = 1, max = 3)
    private String currency;

    @NotNull
    private Long totalAmount;

    @NotNull
    private Long balanceAmount;

    @NotNull
    private Long suppliedAmount;

    @NotNull
    private Long vat;

    @NotNull
    private Long taxFreeAmount;

    @NotBlank
    @Size(min = 6, max = 21)
    private String method;

}
