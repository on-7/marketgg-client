package com.nhnacademy.marketgg.client.web.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CouponRequest;
import com.nhnacademy.marketgg.client.dto.response.CouponRetrieveResponse;
import com.nhnacademy.marketgg.client.service.CouponService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 쿠폰 관련된 Controller 입니다.
 *
 * @since 1.0.0
 */
@Controller
@RequestMapping("/admin/coupons")
@RequiredArgsConstructor
public class AdminCouponController {

    private final CouponService couponService;

    /**
     * 쿠폰 등록 페이지로 이동하는 메소드입니다.
     *
     * @return 쿠폰 등록 페이지로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/create")
    public ModelAndView doCreateCoupon() {
        return new ModelAndView("/coupons/create-form");
    }

    /**
     * 쿠폰을 등록하고 쿠폰 index 페이지로 이동하는 메소드입니다.
     *
     * @param couponRequest - 쿠폰 등록을 위한 정보를 담은 객체입니다.
     * @return - 쿠폰 index 페이지로 REDIRECT 합니다.
     * @throws JsonProcessingException - Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    @PostMapping
    public ModelAndView createCoupon(@ModelAttribute final CouponRequest couponRequest) throws JsonProcessingException {
        couponService.createCoupon(couponRequest);

        return new ModelAndView("redirect:/shop/v1/admin/coupons/index");
    }

    /**
     * 쿠폰 전체 목록을 조회한 정보를 담아 index 페이지로 이동하는 메소드입니다.
     *
     * @return 쿠폰 전체 목록 정보와 함께 쿠폰 index 페이지로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/index")
    public ModelAndView retrieveCoupons() {
        List<CouponRetrieveResponse> responses = couponService.retrieveCoupons();

        ModelAndView mav = new ModelAndView("/coupons/index");
        mav.addObject("coupons", responses);

        return mav;
    }

    /**
     * 쿠폰 수정을 위해 해당 쿠폰을 조회한 정보를 담고 쿠폰 수정 페이지로 이동하는 메소드입니다.
     *
     * @param couponId - 수정을 하려는 쿠폰 식별번호입니다.
     * @return 수정하려는 쿠폰 정보를 담고 쿠폰 수정 페이지로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/update/{couponId}")
    public ModelAndView doUpdateCoupon(@PathVariable final Long couponId) {
        ModelAndView mav = new ModelAndView("/coupons/update-form");

        CouponRetrieveResponse couponResponse = couponService.retrieveCoupon(couponId);
        mav.addObject("coupon", couponResponse);

        return mav;
    }

    /**
     * 쿠폰을 수정하고 쿠폰 index 페이지로 이동하는 메소드입니다.
     *
     * @param couponId      - 수정하려는 쿠폰 식별번호입니다.
     * @param couponRequest - 수정할 내용을 담고 있는 객체입니다.
     * @return 쿠폰 index 페이지로 REDIRECT 합니다.
     * @throws JsonProcessingException - Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    @PutMapping("/{couponId}")
    public ModelAndView updateCoupon(@PathVariable final Long couponId,
                                     @ModelAttribute final CouponRequest couponRequest) throws JsonProcessingException {
        couponService.updateCoupon(couponId, couponRequest);

        return new ModelAndView("redirect:/shop/v1/admin/coupons/index");
    }

    /**
     * 쿠폰을 삭제하고 쿠폰 index 페이지로 이동하는 메소드입니다.
     *
     * @param couponId - 삭제하려는 쿠폰 식별번호입니다.
     * @return 쿠폰 index 페이지로 REDIRECT 합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/{couponId}")
    public ModelAndView deleteCoupon(@PathVariable final Long couponId) {
        couponService.deleteCoupon(couponId);

        return new ModelAndView("redirect:/shop/v1/admin/coupons/index");
    }

}
