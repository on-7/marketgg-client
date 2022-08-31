package com.nhnacademy.marketgg.client.service.payment;

import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.payment.PaymentCancelRequest;
import com.nhnacademy.marketgg.client.dto.payment.PaymentConfirmRequest;
import com.nhnacademy.marketgg.client.dto.payment.PaymentFailureResult;
import com.nhnacademy.marketgg.client.dto.payment.PaymentVerifyRequest;

/**
 * Market GG 결제 관련 기능을 수행합니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
public interface PaymentService {

    /**
     * 회원의 결제 요청에 대한 검증을 처리합니다.
     *
     * @param paymentRequest - 결제 검증 요청 정보
     */
    void verifyRequest(final PaymentVerifyRequest paymentRequest);

    /**
     * 최종 결제 승인을 처리합니다.
     *
     * @param paymentRequest - 결제 승인 요청 정보
     */
    void pay(final PaymentConfirmRequest paymentRequest);

    /**
     * 결제 내역을 조회합니다.
     *
     * @param paymentKey - 결제 건에 대한 고유 키 값
     */
    void retrievePayment(final String paymentKey);

    /**
     * 결제 승인 요청 도중 실패 시 fallback 기능을 수행합니다.
     *
     * @param paymentResult - 결제 실패 결과 정보
     * @return 결제 실패 결과가 담긴 공통 응답 객체
     */
    CommonResult<PaymentFailureResult> requestFail(final PaymentFailureResult paymentResult);

    /**
     * 승인된 결제를 취소합니다.
     *
     * @param paymentKey     - 결제 건에 대한 고유 키 값
     * @param paymentRequest - 결제 취소 요청 객체
     */
    void cancelPayment(final String paymentKey, final PaymentCancelRequest paymentRequest);

}
