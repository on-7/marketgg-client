package com.nhnacademy.marketgg.client.repository.payment;

import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.payment.PaymentCancelRequest;
import com.nhnacademy.marketgg.client.dto.payment.PaymentConfirmRequest;
import com.nhnacademy.marketgg.client.dto.payment.PaymentResponse;
import com.nhnacademy.marketgg.client.dto.payment.PaymentVerifyRequest;
import java.util.Collections;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * {@inheritDoc}
 */
@Component
@RequiredArgsConstructor
public class PaymentAdapter implements PaymentRepository {

    private final String gatewayIp;

    /**
     * {@inheritDoc}
     *
     * @param paymentRequest - 결제 검증 요청 정보
     */
    @Override
    public void verifyRequest(final PaymentVerifyRequest paymentRequest) {
        WebClient.builder()
                 .baseUrl(gatewayIp)
                 .defaultHeaders(httpHeaders -> {
                     httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                     httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                 })
                 .build()
                 .post()
                 .uri("/shop/v1/payments/verify")
                 .bodyValue(paymentRequest)
                 .retrieve()
                 .bodyToMono(Void.class)
                 .block();
    }

    /**
     * {@inheritDoc}
     *
     * @param paymentRequest - 결제 승인 요청 정보
     */
    @Override
    public PaymentResponse pay(final PaymentConfirmRequest paymentRequest) {
        ResponseEntity<CommonResult<PaymentResponse>> response
            = WebClient.builder()
                       .baseUrl(gatewayIp)
                       .defaultHeaders(httpHeaders -> {
                           httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                           httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                       })
                       .build()
                       .post()
                       .uri("/shop/v1/payments/confirm")
                       .bodyValue(paymentRequest)
                       .retrieve()
                       .toEntity(new ParameterizedTypeReference<CommonResult<PaymentResponse>>() {
                       })
                       .blockOptional()
                       .orElseThrow(RuntimeException::new);

        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public void retrievePayment(final String paymentKey) {
        WebClient.builder()
                 .baseUrl(gatewayIp)
                 .defaultHeaders(httpHeaders -> {
                     httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                     httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                 })
                 .build()
                 .get()
                 .uri("/shop/v1/payments/" + paymentKey)
                 .retrieve()
                 .bodyToMono(Void.class)
                 .block();
    }

    /**
     * {@inheritDoc}
     *
     * @param paymentKey     - 결제 건에 대한 고유 키 값
     * @param paymentRequest - 결제 취소 요청 객체
     */
    @Override
    public void cancelPayment(final String paymentKey, final PaymentCancelRequest paymentRequest) {
        WebClient.builder()
                 .baseUrl(gatewayIp)
                 .defaultHeaders(httpHeaders -> {
                     httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                     httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                 })
                 .build()
                 .post()
                 .uri("/shop/v1/payments/" + paymentKey + "/cancel")
                 .bodyValue(paymentRequest)
                 .retrieve()
                 .bodyToMono(Void.class)
                 .block();
    }

}
