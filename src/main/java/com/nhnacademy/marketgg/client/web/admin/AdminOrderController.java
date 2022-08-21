package com.nhnacademy.marketgg.client.web.admin;

import com.nhnacademy.marketgg.client.service.order.OrderService;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 주문관리를 위한 컨트롤러 입니다.
 *
 * @author 김훈민
 * @version 1.0.0
 */
@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    /**
     * 관리자가 운송장 번호를 만들기 위한 메소드 입니다.
     *
     * @param orderNo - 주문 번호
     * @return 운송장 번호 만들기를 요청하고 난뒤 이동할 페이지(구축 필요)
     */
    @PatchMapping("/{orderNo}/delivery")
    public ModelAndView createTrackingNo(@PathVariable @Min(1) final Long orderNo) {

        orderService.createTrackingNo(orderNo);
        // TODO: 관리자 View 단 작업필요.
        return new ModelAndView("redirect:/");
    }

}
