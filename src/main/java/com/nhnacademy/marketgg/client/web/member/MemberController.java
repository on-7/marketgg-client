package com.nhnacademy.marketgg.client.web.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.PageResult;
import com.nhnacademy.marketgg.client.dto.request.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.client.dto.response.GivenCouponRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.service.GivenCouponService;
import com.nhnacademy.marketgg.client.service.ProductInquiryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ModelAndView retrieveOwnCoupons()
        throws UnAuthenticException, UnAuthorizationException {

        PageResult<GivenCouponRetrieveResponse> responses = givenCouponService.retrieveOwnGivenCoupons();

        ModelAndView mav = new ModelAndView("pages/mygg/coupons/index");
        mav.addObject("coupons", responses.getData());
        mav.addObject("pageNumber", responses.getPageNumber());
        mav.addObject("pageSize", responses.getPageSize());
        mav.addObject("totalPages", responses.getTotalPages());
        mav.addObject("memberId", responses.getData().get(0).getMemberId());

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
    public ModelAndView retrieveProductInquiry()
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        List<ProductInquiryResponse> inquiries = this.inquiryService.retrieveInquiryByMember();

        ModelAndView mav = new ModelAndView("mygg/inquiries/index");
        mav.addObject("inquiries", inquiries);

        return mav;
    }

}
