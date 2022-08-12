package com.nhnacademy.marketgg.client.web.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.context.SessionContext;
import com.nhnacademy.marketgg.client.dto.Alert;
import com.nhnacademy.marketgg.client.dto.request.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberUpdateToAuth;
import com.nhnacademy.marketgg.client.dto.response.GivenCouponRetrieveResponse;
<<<<<<< HEAD
import com.nhnacademy.marketgg.client.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
=======
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
>>>>>>> f6eadbd (refactor: 회원정보 메서드 이전)
import com.nhnacademy.marketgg.client.service.GivenCouponService;
import com.nhnacademy.marketgg.client.service.MemberService;
import com.nhnacademy.marketgg.client.service.ProductInquiryService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    private static final String REDIRECT = "redirect:";

    private final MemberService memberService;

    private final GivenCouponService givenCouponService;
    private final ProductInquiryService inquiryService;

    private static final String DEFAULT_MEMBER = "/members";

    /**
     * 회원의 GG 패스 화면으로 이동합니다.
     *
     * @return 회원의 식별번호를 담고, GG 패스의 index 페이지로 이동합니다.
     * @author 박세완
     * @since 1.0.0
     */
    @GetMapping("/ggpass")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("pages/ggpass/index");
        mav.addObject("time", LocalDate.from(memberService.retrievePassUpdatedAt()));

        return mav;
    }

    /**
     * 회원을 GG 패스에 구독시키는 Mapping 을 지원합니다.
     *
     * @return 갱신일자가 지금 시간보다 늦을 시, ERROR 메세지와 함께 다시 Index 페이지로 이동합니다.
     * 갱신일자가 지금 시간보다 빠를 시, 구독하는 메소드를 실행한 후, 다시 Index 페이지로 이동합니다.
     * @author 박세완
     * @since 1.0.0
     */
    @PostMapping("/ggpass/subscribe")
    public ModelAndView subscribePass() {
        if (memberService.retrievePassUpdatedAt().isAfter(LocalDateTime.now())) {
            ModelAndView mav = new ModelAndView("message");
            mav.addObject("message", new Alert("이미 구독하신 상태입니다.",
                                               DEFAULT_MEMBER + "/ggpass"));
            return mav;
        }
        memberService.subscribePass();

        return new ModelAndView(REDIRECT + DEFAULT_MEMBER + "/ggpass");
    }

    /**
     * 선택한 회원을 GG 패스에 구독해지시키는 Mapping 을 지원합니다.
     *
     * @return GG 패스 구독을 해지하는 메소드 실행 후, 다시 Index 페이지로 이동합니다.
     * @author 박세완
     * @since 1.0.0
     */
    @PostMapping("/ggpass/withdraw")
    public ModelAndView withdrawPass() {
        memberService.withdrawPass();

        return new ModelAndView(REDIRECT + DEFAULT_MEMBER + "/ggpass");
    }

    /**
     * 회원이 쿠폰을 등록할 수 있는 POST Mapping 을 지원합니다.
     *
     * @param memberId           - 쿠폰을 등록하는 회원의 식별번호입니다.
     * @param givenCouponRequest - 쿠폰을 등록하기 위한 DTO 객체입니다.
     * @return 쿠폰 index 페이지로 REDIRECT 합니다.
     * @throws JsonProcessingException - Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    @PostMapping("/{memberId}/coupons")
    public ModelAndView registerCoupon(@PathVariable final Long memberId,
                                       @ModelAttribute final GivenCouponCreateRequest givenCouponRequest)
            throws JsonProcessingException {
        givenCouponService.registerCoupon(memberId, givenCouponRequest);

        return new ModelAndView(REDIRECT + DEFAULT_MEMBER + "/" + memberId + "/coupons");
    }

    /**
     * 회원의 보유 쿠폰 목록을 조회할 수 있는 GET Mapping 을 지원합니다.
     *
     * @param memberId - 쿠폰 목록을 조회할 회원의 식별번호입니다.
     * @return 회원의 식별번호와 조회한 쿠폰 목록 정보와 함께 쿠폰 index 페이지로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/{memberId}/coupons")
    public ModelAndView retrieveOwnCoupons(@PathVariable final Long memberId) {
        List<GivenCouponRetrieveResponse> responses = givenCouponService.retrieveOwnGivenCoupons(memberId);

        ModelAndView mav = new ModelAndView("mygg/coupons/index");
        mav.addObject("coupons", responses);
        mav.addObject("memberId", memberId);

        return mav;
    }

<<<<<<< HEAD
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
=======

    /**
     * 회원정보 수정 요청을 받아 회원정보 수정 프로세스를 진행합니다.
     *
     * @param memberUpdateToAuth - 회원정보 수정에 필요한 요청 정보 객체 (Auth 정보만 수정됨)  입니다.
     * @return 회원수정 실행 후, 다시 Index 페이지로 이동합니다.
     * @author 김훈민
     * @since 1.0.0
     */
    @PostMapping("/update")
    public ModelAndView doUpdate(@ModelAttribute MemberUpdateToAuth memberUpdateToAuth)
        throws UnAuthenticException {
        String sessionId = SessionContext.get()
                                         .orElseThrow(UnAuthenticException::new);

        memberService.update(memberUpdateToAuth, sessionId);
        return new ModelAndView(REDIRECT);
    }

    /**
     * 회원탈퇴 요청을 받아 회원탈퇴 프로세스를 진행합니다.
     *
     * @return 회원탈퇴 실행 후, 다시 Index 페이지로 이동합니다.
     * @author 김훈민
     */
    @GetMapping("/withdraw")
    public ModelAndView doWithdraw() throws UnAuthenticException {
        String sessionId = SessionContext.get()
                                         .orElseThrow(UnAuthenticException::new);

        memberService.withdraw(sessionId);
        return new ModelAndView(REDIRECT);
>>>>>>> f6eadbd (refactor: 회원정보 메서드 이전)
    }

}
