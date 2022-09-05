package com.nhnacademy.marketgg.client.web.payment;

import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.payment.PaymentCancelRequest;
import com.nhnacademy.marketgg.client.dto.payment.PaymentConfirmRequest;
import com.nhnacademy.marketgg.client.dto.payment.PaymentFailureResult;
import com.nhnacademy.marketgg.client.dto.payment.PaymentResponse;
import com.nhnacademy.marketgg.client.dto.payment.PaymentVerifyRequest;
import com.nhnacademy.marketgg.client.dto.payment.VirtualAccountDepositRequest;
import com.nhnacademy.marketgg.client.service.payment.PaymentService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 결제 요청에 대한 결정을 처리해주는 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 결제 정보 검증 객체의 주문 번호를 통해 저장된 결제 명세를 조회해서 현재 금액과 결제 요청 시 저장한 금액과 비교합니다.
     * 만약 금액이 일치한다면 정상적으로 가격 이상 없이 주문이 이루어진 것을 검증할 수 있습니다.
     *
     * @param paymentRequest - 결제 요청 검증을 위한 객체
     * @return 결제 승인 요청 페이지 혹은 검증 실패 페이지
     */
    @PostMapping("/payments/verify")
    public ModelAndView verifyPayment(@ModelAttribute @Valid final PaymentVerifyRequest paymentRequest) {
        log.info("verifyPayment: {}", paymentRequest);
        paymentService.verifyRequest(paymentRequest);

        return new ModelAndView("redirect:/payments/request-payment");
    }

    /**
     * 결제 요청 결과가 성공적이라면 결제대행사로 최종 결제 승인 요청을 보냅니다.
     *
     * @param orderId    - Market GG 고유 주문 번호
     * @param paymentKey - 결제 건에 대한 고유한 키 값
     * @param amount     - 실제 결제된 금액
     * @return 결제 성공 혹은 실패 페이지
     */
    @GetMapping("/payments/success")
    public ModelAndView confirmPayment(@RequestParam final String orderId, @RequestParam final String paymentKey,
                                       @RequestParam final Long amount, RedirectAttributes attributes) {

        PaymentConfirmRequest paymentRequest = PaymentConfirmRequest.create(orderId, paymentKey, amount);
        log.info("confirmPayment: {}", paymentRequest);

        PaymentResponse result = paymentService.pay(paymentRequest);
        attributes.addFlashAttribute("paymentResponse", result);

        return new ModelAndView("redirect:/payments/success-payment");
    }

    /**
     * 결제 실패 시 fallback 을 수행합니다.
     * 테스트용 키로는 결제 불가능한 카드로 결제를 시도해도 결제 실패가 나지 않습니다.
     *
     * @param code    - 오류 타입을 보여주는 에러 코드
     * @param message - 에러 발생 이유가 담긴 에러 메시지
     * @param orderId - Market GG 고유 주문 번호
     * @return 예외 처리 페이지
     */
    @GetMapping("/payments/fail")
    public ModelAndView redirectWhenPaymentRequestFailed(@RequestParam final String code,
                                                         @RequestParam final String message,
                                                         @RequestParam final String orderId) {

        PaymentFailureResult paymentResult = PaymentFailureResult.create(code, message, orderId);
        log.info("redirectWhenPaymentRequestFailed: {}", paymentResult);
        CommonResult<PaymentFailureResult> result = paymentService.requestFail(paymentResult);

        ModelAndView mav = new ModelAndView("redirect:/error");
        mav.addObject("data", result.getData());

        return mav;
    }

    @PostMapping("/payments/event/virtual-accounts")
    public ModelAndView callback(@ModelAttribute final VirtualAccountDepositRequest virtualAccountRequest) {

        ModelAndView mav = new ModelAndView("redirect:/error");

        return mav;
    }

    @GetMapping("/payments/event/virtual-accounts")
    public ModelAndView callback2(@ModelAttribute final VirtualAccountDepositRequest virtualAccountRequest) {

        ModelAndView mav = new ModelAndView("redirect:/error");

        return mav;
    }

    /**
     * 승인된 결제를 취소하려고 시도할 때, 해당 요청을 처리합니다.
     *
     * @param paymentKey     - 결제 건에 대한 고유한 키 값
     * @param paymentRequest - 결제 취소 요청 객체
     * @return 결제 취소 처리 여부 페이지
     */
    @PostMapping("/payments/{paymentKey}/cancel")
    public ModelAndView cancelPayment(@PathVariable final String paymentKey,
                                      @ModelAttribute final PaymentCancelRequest paymentRequest) {

        paymentService.cancelPayment(paymentKey, paymentRequest);

        return new ModelAndView("redirect:/");
    }

}
