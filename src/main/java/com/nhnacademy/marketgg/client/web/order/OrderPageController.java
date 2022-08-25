package com.nhnacademy.marketgg.client.web.order;

import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.order.OrderRetrieveResponse;
import com.nhnacademy.marketgg.client.service.order.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * 주문과 관련된 페이지 요청 처리를 담당합니다.
 *
 * @author 이제훈, 김정민
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderPageController {

    private final OrderService orderService;

    /**
     * 주문서 작성 폼으로 페이지를 이동시켜주는 역할을 담당합니다.
     *
     * @return 주문서 작성 페이지
     */
    @GetMapping("/orders/form")
    public ModelAndView goOrderForm(final MemberInfo memberInfo) {
        ModelAndView mav = new ModelAndView("pages/orders/order-form");
        mav.addObject(memberInfo);

        return mav;
    }

    /**
     * 주문 목록을 조회합니다.
     *
     * @return 주문 목록과 페이지를 포함한 객체
     */
    @GetMapping("/orders")
    public ModelAndView retrieveOrders() {
        List<OrderRetrieveResponse> orders = orderService.retrieveOrders();
        log.info("retrieveOrders: {}", orders);

        ModelAndView mav = new ModelAndView("pages/orders/order-list");
        mav.addObject("orders", orders);

        return mav;
    }

    /**
     * 주문 상세 조회를 처리합니다.
     *
     * @param orderId - 주문 번호
     * @return 주문 상세 정보와 페이지를 포함한 객체
     */
    @GetMapping("/orders/{orderId}")
    public ModelAndView retrieveOrder(@PathVariable final Long orderId) {
        OrderDetailRetrieveResponse order = orderService.retrieveOrder(orderId);
        log.info("retrieveOrder: {}", order);

        ModelAndView mav = new ModelAndView("/pages/orders/order-details");
        mav.addObject("order", order);

        return mav;
    }

}
