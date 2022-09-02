package com.nhnacademy.marketgg.client.web.admin;

import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.order.OrderRetrieveResponse;
import com.nhnacademy.marketgg.client.paging.Pagination;
import com.nhnacademy.marketgg.client.service.order.OrderService;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 주문관리를 위한 컨트롤러 입니다.
 *
 * @author 김훈민
 * @version 1.0.0
 */
@Slf4j
@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping("/index")
    public ModelAndView adminRetrieveOrders(@RequestParam(defaultValue = "1") final Integer page) {
        PageResult<OrderRetrieveResponse> orders = orderService.retrieveOrders(page);

        log.info("retrieveOrders: {}", orders);

        Pagination pagination = new Pagination(orders.getTotalPages(), page);
        List<OrderRetrieveResponse> orderList = orders.getData();

        ModelAndView mav = new ModelAndView("pages/admin/orders/order-list");
        mav.addObject("orders", orderList);
        mav.addObject("pages", pagination);

        return mav;
    }

    @GetMapping("/{orderId}")
    public ModelAndView adminRetrieveOrderDetail(@PathVariable final Long orderId) {
        log.info("retrieveOrderDetail: {}", orderId);

        OrderDetailRetrieveResponse order = orderService.retrieveOrder(orderId);

        ModelAndView mav = new ModelAndView("pages/admin/orders/order-details");
        mav.addObject("order", order);

        return mav;
    }

    /**
     * 관리자가 운송장 번호를 만들기 위한 메소드 입니다.
     *
     * @param orderNo - 주문 번호
     * @return 운송장 번호 만들기를 요청하고 난뒤 이동할 페이지(구축 필요)
     */
    @GetMapping("/{orderNo}/delivery")
    public ModelAndView createTrackingNo(@PathVariable @Min(1) final Long orderNo) {

        orderService.createTrackingNo(orderNo);

        return new ModelAndView("redirect:/admin/orders/index");
    }

}
