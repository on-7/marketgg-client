package com.nhnacademy.marketgg.client.web.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.context.SessionContext;
import com.nhnacademy.marketgg.client.dto.Alert;
import com.nhnacademy.marketgg.client.dto.request.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.DeliveryAddressUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberUpdateToAuth;
import com.nhnacademy.marketgg.client.dto.response.DeliveryAddressResponse;
import com.nhnacademy.marketgg.client.dto.response.GivenCouponRetrieveResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.service.GivenCouponService;
import com.nhnacademy.marketgg.client.service.MemberService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        ModelAndView mav = new ModelAndView("/ggpass/index");
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

        ModelAndView mav = new ModelAndView("/mygg/coupons/index");
        mav.addObject("coupons", responses);
        mav.addObject("memberId", memberId);

        return mav;
    }


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
     * @since 1.0.0
     */
    @GetMapping("/withdraw")
    public ModelAndView doWithdraw() throws UnAuthenticException {
        String sessionId = SessionContext.get()
                                         .orElseThrow(UnAuthenticException::new);

        memberService.withdraw(sessionId);
        return new ModelAndView(REDIRECT);
    }

    /**
     * 회원이 보유한 배송지 목록 리스트를 보여줍니다.
     *
     * @return 회원이 보유한 배송지 목록 리스트 입니다.
     * @author 김훈민
     * @since 1.0.0
     */
    @GetMapping("/delivery-addresses")
    public ModelAndView deliveryAddresses() {
        List<DeliveryAddressResponse> deliveryAddressResponseList = memberService.retrieveDeliveryAddresses();
        // TODO : 페이지 돌입지점에서 바로 보여줘야함. 페이지 필요.
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("deliveryAddressList", deliveryAddressResponseList);
        return modelAndView;
    }

    /**
     * 회원이 기본 배송지 이외에 다른 배송지를 추가합니다.
     *
     * @param addressRequest - 추가하는 배송지의 정보를 담고있는 DTO 입니다.
     * @return 배송지 추가하고있는 페이지 redirect
     * @author 김훈민
     * @since 1.0.0
     */
    @PostMapping("/delivery-address")
    public ModelAndView createDeliveryAddress(
        @ModelAttribute @Valid final DeliveryAddressCreateRequest addressRequest) {

        memberService.createDeliveryAddress(addressRequest);
        return new ModelAndView(REDIRECT);
    }

    /**
     * 회원이 가진 배송지 정보를 수정하는 PutMapping 메소드 입니다.
     *
     * @param updateRequest - 수정하는 배송지의 정보를 담고있는 DTO 입니다.
     * @return 배송지 수정하고있는 페이지 redirect
     * @author 김훈민
     * @since 1.0.0
     */
    @PutMapping("/delivery-address")
    public ModelAndView updateDeliveryAddress(@ModelAttribute @Valid final DeliveryAddressUpdateRequest updateRequest) {

        memberService.updateDeliveryAddress(updateRequest);
        return new ModelAndView(REDIRECT);
    }

}
