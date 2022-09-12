package com.nhnacademy.marketgg.client.dto.payment;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * 결제 승인 요청을 검증하기 위한 객체입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public class PaymentConfirmRequest {

    @NotBlank
    @Size(min = 6, max = 64)
    private final String orderId;

    @NotBlank
    @Size(max = 200)
    private final String paymentKey;

    @NotNull
    private final Long amount;

    private Optional<Long> couponId;

    @NotNull
    private Integer usedPoint;

    @NotNull
    private List<Long> orderedProducts;

    public static PaymentConfirmRequest create(final String orderId, final String paymentKey, final Long amount) {
        return new PaymentConfirmRequest(orderId, paymentKey, amount);
    }

    public void addOrderInfo(final HttpSession session) {
        couponId = Optional.ofNullable((Long) session.getAttribute("usedCouponId"));
        usedPoint = (Integer) session.getAttribute("usedPoint");
        orderedProducts = (List<Long>) session.getAttribute("orderedProducts");
    }

}
