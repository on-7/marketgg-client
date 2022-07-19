package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CouponRequest;
import com.nhnacademy.marketgg.client.dto.response.CouponRetrieveResponse;
import com.nhnacademy.marketgg.client.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/shop/v1/admin/coupons")
@RequiredArgsConstructor
public class AdminCouponController {

    private final CouponService couponService;

    @GetMapping("/create")
    public ModelAndView doCreateCoupon() {
        return new ModelAndView("/coupons/create-form");
    }

    @PostMapping
    public ModelAndView createCoupon(@ModelAttribute final CouponRequest couponRequest) throws JsonProcessingException {
        couponService.createCoupon(couponRequest);

        return new ModelAndView("redirect:/shop/v1/admin/coupons/index");
    }

    @GetMapping("/index")
    public ModelAndView retrieveCoupons() {
        List<CouponRetrieveResponse> responses = couponService.retrieveCoupons();

        ModelAndView mav = new ModelAndView("/coupons/index");
        mav.addObject("coupons", responses);

        return mav;
    }

    @GetMapping("/update/{couponId}")
    public ModelAndView doUpdateCoupon(@PathVariable final Long couponId) {
        ModelAndView mav = new ModelAndView("/coupons/update-form");

        CouponRetrieveResponse couponResponse = couponService.retrieveCoupon(couponId);
        mav.addObject("coupon", couponResponse);

        return mav;
    }

    @PutMapping("/{couponId}")
    public ModelAndView updateCoupon(@PathVariable final Long couponId,
                                     @ModelAttribute final CouponRequest couponRequest) throws JsonProcessingException {
        couponService.updateCoupon(couponId, couponRequest);

        return new ModelAndView("redirect:/shop/v1/admin/coupons/index");
    }

}
