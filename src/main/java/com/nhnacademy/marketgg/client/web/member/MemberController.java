package com.nhnacademy.marketgg.client.web.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.common.MemberInfo;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.coupon.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.client.dto.coupon.GivenCouponRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.product.ProductInquiryResponse;
import com.nhnacademy.marketgg.client.dto.review.ReviewResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.paging.Pagination;
import com.nhnacademy.marketgg.client.service.coupon.GivenCouponService;
import com.nhnacademy.marketgg.client.service.product.ProductInquiryService;
import com.nhnacademy.marketgg.client.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 회원관리에 관련된 Controller 입니다.
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private static final String REDIRECT = "redirect:/members";

    private final GivenCouponService givenCouponService;
    private final ProductInquiryService inquiryService;
    private final ReviewService reviewService;

    /**
     * 회원이 쿠폰을 등록할 수 있는 POST Mapping 을 지원합니다.
     *
     * @param givenCouponRequest - 쿠폰을 등록하기 위한 DTO 객체입니다.
     * @return 쿠폰 index 페이지로 REDIRECT 합니다.
     * @throws JsonProcessingException - Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    @PostMapping("/coupons")
    public ModelAndView registerCoupon(@ModelAttribute final GivenCouponCreateRequest givenCouponRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        givenCouponService.registerCoupon(givenCouponRequest);

        return new ModelAndView(REDIRECT + "/coupons");
    }

    /**
     * 회원의 보유 쿠폰 목록을 조회할 수 있는 GET Mapping 을 지원합니다.
     *
     * @return 회원의 식별번호와 조회한 쿠폰 목록 정보와 함께 쿠폰 index 페이지로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/coupons")
    public ModelAndView retrieveOwnCoupons(@RequestParam(defaultValue = "1") final Integer page)
            throws UnAuthenticException, UnAuthorizationException {

        PageResult<GivenCouponRetrieveResponse> responses = givenCouponService.retrieveOwnGivenCoupons(page);
        Pagination pagination = new Pagination(responses.getTotalPages(), page);

        ModelAndView mav = new ModelAndView("pages/mygg/coupons/index");
        mav.addObject("coupons", responses.getData());
        mav.addObject("pages", pagination);

        return mav;
    }

    /**
     * 회원이 작성한 전체 상품 문의 조회할 수 있는 @GetMapping 을 지원합니다.
     *
     * @return 회원이 작성한 모든 상품 문의 목록 정보를 가지고 My gg 페이지의 상품 문의 조회 페이지로 이동합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    @GetMapping("/product-inquiries")
    public ModelAndView retrieveProductInquiry(@RequestParam(defaultValue = "1") final Integer page)
            throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        PageResult<ProductInquiryResponse> inquiries = this.inquiryService.retrieveInquiryByMember(page);
        Pagination pagination = new Pagination(inquiries.getTotalPages(), page);

        ModelAndView mav = new ModelAndView("pages/mygg/inquiries/index");
        mav.addObject("inquiries", inquiries.getData());
        mav.addObject("pages", pagination);

        return mav;
    }

    @GetMapping("/reviews")
    public ModelAndView retrieveReviewsByMember(final MemberInfo memberInfo, @RequestParam(defaultValue = "1") int page) {
        PageResult<ReviewResponse> reviewResponsePageResult = reviewService.retrieveReviewsByMember(memberInfo, page);

        ModelAndView mav = new ModelAndView("pages/mygg/reviews/index");
        mav.addObject("reviews", reviewResponsePageResult.getData());

        Pagination pagination = new Pagination(reviewResponsePageResult.getTotalPages(), page);
        mav.addObject("pages", pagination);

        return mav;
    }

}
