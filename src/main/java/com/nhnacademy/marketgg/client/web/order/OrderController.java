package com.nhnacademy.marketgg.client.web.order;

import com.nhnacademy.marketgg.client.dto.common.MemberInfo;
import com.nhnacademy.marketgg.client.dto.order.OrderCreateRequest;
import com.nhnacademy.marketgg.client.dto.order.OrderToPayment;
import com.nhnacademy.marketgg.client.service.order.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 주문에 대한 요청과 응답을 처리하는 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문서에서 작성한 정보를 바탕으로 주문을 생성하는 요청 처리를 담당합니다.
     *
     * @param orderRequest - 주문 생성 시 필요한 요청 정보 객체
     */
    @PostMapping("/orders")
    public ModelAndView createOrder(@ModelAttribute @Valid final OrderCreateRequest orderRequest,
                                    MemberInfo memberInfo, RedirectAttributes attributes, HttpSession session) {

        OrderToPayment orderToPayment = orderService.createOrder(orderRequest, memberInfo);
        attributes.addFlashAttribute("orderToPayment", orderToPayment);

        session.setAttribute("usedCouponId", orderToPayment.getCouponId());
        session.setAttribute("usedPoint", orderToPayment.getUsedPoint());
        session.setAttribute("orderedProducts", orderRequest.getProductIds());

        return new ModelAndView("redirect:/payments/request-payment");
    }

    /**
     * 주문 취소를 처리합니다.
     *
     * @param orderId - 주문 번호
     * @return 주문 취소 이후 이동할 페이지를 포함한 객체
     */
    @PutMapping("/orders/{orderId}")
    public ModelAndView cancelOrders(@PathVariable final Long orderId) {
        orderService.cancelOrder(orderId);

        return new ModelAndView("redirect:/");
    }

}
