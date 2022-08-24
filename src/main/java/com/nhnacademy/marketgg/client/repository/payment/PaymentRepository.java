package com.nhnacademy.marketgg.client.repository.payment;

import com.nhnacademy.marketgg.client.dto.payment.PaymentCancelRequest;
import com.nhnacademy.marketgg.client.dto.payment.PaymentConfirmRequest;
import com.nhnacademy.marketgg.client.dto.payment.PaymentVerifyRequest;

/**
 * 결제와 관련된 기능을 수행합니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
public interface PaymentRepository {

    /**
     * 회원의 결제 요청에 대한 검증을 처리합니다.
     *
     * @param paymentRequest - 결제 검증 요청 정보
     */
    void verifyRequest(final PaymentVerifyRequest paymentRequest);

    /**
     * 최종 결제를 진행합니다.
     *
     * @param paymentRequest - 결제 승인 요청 정보
     */
    void pay(final PaymentConfirmRequest paymentRequest);

    void retrievePayment(String paymentKey);

    /**
     * 승인된 결제에 대해 취소 요청 통신을 진행합니다.
     *
     * @param paymentRequest - 결제 취소 요청 객체
     */
    void cancelPayment(final String paymentKey, final PaymentCancelRequest paymentRequest);
}
