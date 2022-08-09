package com.nhnacademy.marketgg.client.web.order;

import com.nhnacademy.marketgg.client.dto.order.OrderCreateRequest;
import com.nhnacademy.marketgg.client.dto.order.OrderResponse;
import com.nhnacademy.marketgg.client.service.order.OrderService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 주문에 대한 요청과 응답을 처리하는 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문서 작성 폼으로 페이지를 이동시켜주는 역할을 담당합니다.
     *
     * @return 주문서 작성 페이지
     */
    @GetMapping("/form")
    public ModelAndView goOrderForm() {
        return new ModelAndView("pages/orders/order-form");
    }

    /**
     * 주문서에서 작성한 정보를 바탕으로 주문을 생성하는 요청 처리를 담당합니다.
     *
     * @param orderRequest - 주문 생성 시 필요한 요청 정보 객체
     */
    @PostMapping
    public ModelAndView createOrder(@ModelAttribute @Valid final OrderCreateRequest orderRequest) {
        orderService.createOrder(orderRequest);

        return new ModelAndView("redirect:/payments/request-payment");
    }

    /**
     * 주문 목록을 조회합니다.
     *
     * @return 주문 목록과 페이지를 포함한 객체
     */
    @GetMapping("/orders")
    public ModelAndView retrieveOrders() {
        List<OrderResponse> orders = orderService.retrieveOrders();

        ModelAndView mav = new ModelAndView("/pages/orders/order-list");
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
    public ModelAndView retrieveOrders(@PathVariable final Long orderId) {
        OrderResponse order = orderService.retrieveOrder(orderId);

        ModelAndView mav = new ModelAndView("/pages/orders/order-details");
        mav.addObject("order", order);

        return mav;
    }

}
