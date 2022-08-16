package com.nhnacademy.marketgg.client.web.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/request-payment")
    public ModelAndView index() {
        return new ModelAndView("/pages/payments/request-payment");
    }

    @GetMapping("/success-payment")
    public ModelAndView goOrderConfirmation() {
        return new ModelAndView("/pages/payments/success-payment");
    }

    @GetMapping("/cancel-payment")
    public ModelAndView cancel() {
        return new ModelAndView("/pages/payments/cancel-payment");
    }

}
