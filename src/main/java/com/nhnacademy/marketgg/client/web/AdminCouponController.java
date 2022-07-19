package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.dto.response.CouponRetrieveResponse;
import com.nhnacademy.marketgg.client.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/shop/v1/admin/coupons")
@RequiredArgsConstructor
public class AdminCouponController {

    private final CouponService couponService;

    @GetMapping
    public ModelAndView retrieveCoupons() {
        List<CouponRetrieveResponse> responses = couponService.retrieveCoupons();

        ModelAndView mav = new ModelAndView("/coupons/index");
        mav.addObject("coupons", responses);

        return mav;
    }
}
