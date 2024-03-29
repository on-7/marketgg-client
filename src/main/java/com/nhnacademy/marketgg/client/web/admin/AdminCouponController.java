package com.nhnacademy.marketgg.client.web.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.coupon.CouponRequest;
import com.nhnacademy.marketgg.client.dto.coupon.CouponRetrieveResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.paging.Pagination;
import com.nhnacademy.marketgg.client.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private static final String REDIRECT_DEFAULT = "redirect:/admin/coupons/index";

    /**
     * 쿠폰을 등록하고 쿠폰 index 페이지로 이동하는 메소드입니다.
     *
     * @param couponRequest - 쿠폰 등록을 위한 정보를 담은 객체입니다.
     * @return - 쿠폰 index 페이지로 REDIRECT 합니다.
     * @throws JsonProcessingException - Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    @PostMapping
    public ModelAndView createCoupon(@ModelAttribute final CouponRequest couponRequest) throws JsonProcessingException,
        UnAuthenticException, UnAuthorizationException {

        couponService.createCoupon(couponRequest);

        return new ModelAndView(REDIRECT_DEFAULT);
    }

    /**
     * 쿠폰 전체 목록을 조회한 정보를 담아 index 페이지로 이동하는 메소드입니다.
     *
     * @return 쿠폰 전체 목록 정보와 함께 쿠폰 index 페이지로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/index")
    public ModelAndView retrieveCoupons(@RequestParam(defaultValue = "1") final Integer page)
        throws UnAuthenticException, UnAuthorizationException {
        PageResult<CouponRetrieveResponse> responses = couponService.retrieveCoupons(page);

        Pagination pagination = new Pagination(responses.getTotalPages(), page);

        ModelAndView mav = new ModelAndView("pages/admin/coupons/index");
        mav.addObject("coupons", responses.getData());
        mav.addObject("pages", pagination);

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
    public ModelAndView doUpdateCoupon(@PathVariable final Long couponId)
        throws UnAuthenticException, UnAuthorizationException {
        ModelAndView mav = new ModelAndView("pages/admin/coupons/update-form");

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
                                     @ModelAttribute final CouponRequest couponRequest) throws JsonProcessingException,
        UnAuthenticException, UnAuthorizationException {

        couponService.updateCoupon(couponId, couponRequest);

        return new ModelAndView(REDIRECT_DEFAULT);
    }

    /**
     * 쿠폰을 활성화하고 쿠폰 index 페이지로 이동하는 메소드입니다.
     *
     * @param couponId 활성화 시킬 쿠폰 식별번호입니다.
     * @return 쿠폰 index 페이지로 REDIRECT 합니다.
     * @since 1.0.0
     */
    @PutMapping("/{couponId}/activate")
    public ModelAndView activateCoupon(@PathVariable final Long couponId) throws
        UnAuthenticException, UnAuthorizationException {

        couponService.activateCoupon(couponId);

        return new ModelAndView(REDIRECT_DEFAULT);
    }

    /**
     * 쿠폰을 비활성화하고 쿠폰 index 페이지로 이동하는 메소드입니다.
     *
     * @param couponId 비활성화 시킬 쿠폰 식별번호입니다.
     * @return 쿠폰 index 페이지로 REDIRECT 합니다.
     * @since 1.0.0
     */
    @PutMapping("/{couponId}/deactivate")
    public ModelAndView deactivateCoupon(@PathVariable final Long couponId) throws
        UnAuthenticException, UnAuthorizationException {

        couponService.deactivateCoupon(couponId);

        return new ModelAndView(REDIRECT_DEFAULT);
    }

    /**
     * 쿠폰을 삭제하고 쿠폰 index 페이지로 이동하는 메소드입니다.
     *
     * @param couponId - 삭제하려는 쿠폰 식별번호입니다.
     * @return 쿠폰 index 페이지로 REDIRECT 합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/{couponId}")
    public ModelAndView deleteCoupon(@PathVariable final Long couponId)
        throws UnAuthenticException, UnAuthorizationException {
        couponService.deleteCoupon(couponId);

        return new ModelAndView(REDIRECT_DEFAULT);
    }

}
