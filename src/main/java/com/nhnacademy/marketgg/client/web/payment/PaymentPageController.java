package com.nhnacademy.marketgg.client.web.payment;

import com.nhnacademy.marketgg.client.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 결제와 관련된 페이지 요청 처리를 담당합니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentPageController {

    private final PaymentService paymentService;

    @GetMapping("/request-payment")
    public ModelAndView index() {
        return new ModelAndView("pages/payments/request-payment");
    }

    @GetMapping("/success-payment")
    public ModelAndView goOrderConfirmation() {
        return new ModelAndView("pages/payments/success-payment");
    }

    @GetMapping("/cancel-payment")
    public ModelAndView cancel() {
        return new ModelAndView("pages/payments/cancel-payment");
    }

    /**
     * 승인된 결제를 결제키로 조회합니다.
     *
     * @param paymentKey - 결제 건에 대한 고유한 키 값
     * @return 결제 내역 조회 가능 페이지
     */
    @GetMapping("/payments/{paymentKey}")
    public ModelAndView retrievePayment(@PathVariable final String paymentKey) {
        paymentService.retrievePayment(paymentKey);

        return new ModelAndView("/pages/payments/details");
    }

}
