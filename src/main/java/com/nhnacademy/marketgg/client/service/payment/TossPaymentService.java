package com.nhnacademy.marketgg.client.service.payment;

import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.payment.PaymentCancelRequest;
import com.nhnacademy.marketgg.client.dto.payment.PaymentConfirmRequest;
import com.nhnacademy.marketgg.client.dto.payment.PaymentFailureResult;
import com.nhnacademy.marketgg.client.dto.payment.PaymentResponse;
import com.nhnacademy.marketgg.client.dto.payment.PaymentVerifyRequest;
import com.nhnacademy.marketgg.client.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 토스페이먼츠 결제대행사를 통해 결제와 관련된 기능을 수행합니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class TossPaymentService implements PaymentService {

    private final PaymentRepository paymentRepository;

    /**
     * {@inheritDoc}
     *
     * @param paymentRequest - 결제 검증 요청 정보
     */
    @Override
    public void verifyRequest(PaymentVerifyRequest paymentRequest) {
        paymentRepository.verifyRequest(paymentRequest);
    }

    /**
     * {@inheritDoc}
     *
     * @param paymentRequest - 결제 승인 요청 정보
     */
    @Override
    public PaymentResponse pay(PaymentConfirmRequest paymentRequest) {
        return paymentRepository.pay(paymentRequest);
    }

    /**
     * {@inheritDoc}
     *
     * @param paymentKey - 결제 건에 대한 고유 키 값
     */
    @Override
    public void retrievePayment(String paymentKey) {
        paymentRepository.retrievePayment(paymentKey);
    }

    /**
     * {@inheritDoc}
     *
     * @param paymentResult - 결제 실패 결과 정보
     * @return 결제 실패 결과가 담긴 공통 응답 객체
     */
    @Override
    public CommonResult<PaymentFailureResult> requestFail(PaymentFailureResult paymentResult) {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @param paymentKey     - 결제 건에 대한 고유 키 값
     * @param paymentRequest - 결제 취소 요청 객체
     */
    @Override
    public void cancelPayment(final String paymentKey, PaymentCancelRequest paymentRequest) {
        paymentRepository.cancelPayment(paymentKey, paymentRequest);
    }

}
