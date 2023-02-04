package com.nhnacademy.marketgg.client.web.coupon;

import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.coupon.CouponRetrieveResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.paging.Pagination;
import com.nhnacademy.marketgg.client.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.client.web.product.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 메인 페이지에서 쿠폰 탭 Controller 입니다.
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/market-coupons")
@RequiredArgsConstructor
public class CouponController extends BaseController {

    private final CouponRepository couponRepository;

    /**
     * 회원이 등록할 수 있는 쿠폰 목록을 조회할 수 있는 GET Mapping 을 지원합니다.
     *
     * @return 조회한 쿠폰 목록 정보와 함께 쿠폰 조회 index 페이지로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping
    public ModelAndView retrieveOwnCoupons(@RequestParam(defaultValue = "1") final Integer page)
        throws UnAuthenticException, UnAuthorizationException {

        PageResult<CouponRetrieveResponse> responses = couponRepository.retrieveActivateCoupon(page);
        Pagination pagination = new Pagination(responses.getTotalPages(), page);

        ModelAndView mav = new ModelAndView("pages/coupons/index");
        mav.addObject("coupons", responses.getData());
        mav.addObject("pages", pagination);

        return mav;
    }

}
