package com.nhnacademy.marketgg.client.web.member;

import com.nhnacademy.marketgg.client.annotation.NoAuth;
import com.nhnacademy.marketgg.client.dto.common.MemberInfo;
import com.nhnacademy.marketgg.client.dto.delivery.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.client.dto.delivery.DeliveryAddressResponse;
import com.nhnacademy.marketgg.client.dto.member.LoginRequest;
import com.nhnacademy.marketgg.client.dto.member.MemberUpdateRequest;
import com.nhnacademy.marketgg.client.dto.member.SignupRequest;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.service.member.MemberService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 회원관리에 관련된 Controller 입니다.
 *
 * @version 1.0.0
 */
@Controller
@RequiredArgsConstructor
public class MemberInfoController {

    private final MemberService memberService;
    private static final String REDIRECT = "redirect:";
    private static final String REDIRECT_LOGOUT = "redirect:/logout";
    private static final String PAGES = "pages";
    private static final String MEMBER = "/member";
    private static final String MEMBERS = "/members";
    private static final String MYGG = "/mygg";
    private static final String SIGNUP = "/signup";
    private static final String DELIVERY_ADDRESSES = "/delivery-addresses";
    private static final String FORM = "/form";
    private static final String INDEX = "/index";
    private static final String UPDATE_FORM = "/update-form";
    private static final String WITHDRAW_FORM = "/withdraw-form";

    /**
     * 회원가입 View.
     *
     * @return 회원가입 폼
     * @author 김훈민
     * @since 1.0.0
     */
    @NoAuth
    @GetMapping("/signup")
    public ModelAndView signup(@ModelAttribute(name = "member") final SignupRequest member) {
        return new ModelAndView(PAGES + MEMBERS + SIGNUP);
    }

    /**
     * 회원가입 요청을 받아 회원가입을 진행합니다.
     *
     * @param member - 회원가입에 필요한 요청 정보 객체입니다.
     * @return 회원가입 실행 후, 다시 Index 페이지로 이동합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 김훈민
     * @since 1.0.0
     */
    @NoAuth
    @PostMapping("/signup")
    public ModelAndView doSignup(final @Valid @ModelAttribute(name = "member") SignupRequest member,
                                 BindingResult bindingResult) throws UnAuthenticException, UnAuthorizationException {

        if (bindingResult.hasErrors()) {
            return new ModelAndView(PAGES + MEMBERS + SIGNUP);
        }

        memberService.doSignup(member);
        return new ModelAndView(REDIRECT + INDEX);
    }

    @NoAuth
    @GetMapping("/signup/email-check")
    public ModelAndView emailCheckForm() {
        return new ModelAndView(REDIRECT);
    }

    /**
     * 회원 수정 폼 입니다.
     *
     * @param memberUpdateRequest - 회원정보 수정에 필요한 요청 정보 객체 (Auth 정보만 수정됨)  입니다.
     * @return
     */
    @GetMapping("/mygg/member/update")
    public ModelAndView update(
            final @ModelAttribute(name = "memberUpdateRequest") MemberUpdateRequest memberUpdateRequest,
            MemberInfo memberInfo) throws UnAuthenticException, UnAuthorizationException {

        ModelAndView modelAndView = new ModelAndView(PAGES + MYGG + MEMBERS + UPDATE_FORM);
        modelAndView.addObject(memberInfo);
        return modelAndView;
    }

    /**
     * 회원정보 수정 요청을 받아 회원정보 수정 프로세스를 진행합니다.
     *
     * @param memberUpdateRequest - 회원정보 수정에 필요한 요청 정보 객체 (Auth 정보만 수정됨)  입니다.
     * @return 회원수정 실행 후, 다시 Index 페이지로 이동합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 김훈민
     * @since 1.0.0
     */
    @PostMapping("/mygg/member/update")
    public ModelAndView doUpdate(
            final @Valid @ModelAttribute(name = "memberUpdateRequest") MemberUpdateRequest memberUpdateRequest,
            MemberInfo memberInfo,
            BindingResult bindingResult)
            throws UnAuthenticException, UnAuthorizationException {

        if (bindingResult.hasErrors()) {
            return new ModelAndView(PAGES + MYGG + MEMBERS + UPDATE_FORM);
        }

        memberService.update(memberUpdateRequest, memberInfo);
        return new ModelAndView(REDIRECT_LOGOUT);
    }

    @GetMapping("/mygg/member/withdraw")
    public ModelAndView withdraw(final @ModelAttribute("loginRequest") LoginRequest loginRequest)
        throws UnAuthenticException, UnAuthorizationException {

        return new ModelAndView(PAGES + MYGG + MEMBERS + WITHDRAW_FORM);
    }

    /**
     * 회원탈퇴 요청을 받아 회원탈퇴 프로세스를 진행합니다.
     *
     * @return 회원탈퇴 실행 후, 다시 Index 페이지로 이동합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 김훈민
     * @since 1.0.0
     */
    @DeleteMapping("/mygg/member/withdraw")
    public ModelAndView doWithdraw(MemberInfo memberInfo,
                                   final @Valid @ModelAttribute LoginRequest loginRequest,
                                   BindingResult bindingResult) throws UnAuthenticException, UnAuthorizationException {

        if (!(memberInfo.getEmail().equals(loginRequest.getEmail())) || bindingResult.hasErrors()) {
            return new ModelAndView(PAGES + MYGG + MEMBERS + WITHDRAW_FORM);
        }

        memberService.withdraw(loginRequest);
        return new ModelAndView(REDIRECT_LOGOUT);
    }

    /**
     * 회원이 보유한 배송지 목록 리스트를 보여줍니다.
     *
     * @return 회원이 보유한 배송지 목록 리스트 입니다.
     * @author 김훈민
     * @since 1.0.0
     */
    @GetMapping("/mygg/member/delivery-addresses")
    public ModelAndView deliveryAddresses() throws UnAuthenticException, UnAuthorizationException {
        List<DeliveryAddressResponse> deliveryAddressResponseList = memberService.retrieveDeliveryAddresses();
        ModelAndView modelAndView = new ModelAndView(PAGES + MYGG + DELIVERY_ADDRESSES + INDEX);
        modelAndView.addObject("deliveryAddressList", deliveryAddressResponseList);

        return modelAndView;
    }

    /**
     * 배송지 추가를 위한 페이지 입니다.
     *
     * @return 배송지 추가 폼
     */
    @GetMapping("/mygg/member/delivery-address")
    public ModelAndView deliveryAddress(
            final @ModelAttribute(name = "addressRequest") DeliveryAddressCreateRequest addressRequest)
            throws UnAuthenticException, UnAuthorizationException {

        return new ModelAndView(PAGES + MYGG + DELIVERY_ADDRESSES + FORM);
    }

    /**
     * 회원이 기본 배송지 이외에 다른 배송지를 추가합니다.
     *
     * @param addressRequest - 추가하는 배송지의 정보를 담고있는 DTO 입니다.
     * @return 배송지 추가하고있는 페이지 redirect
     * @author 김훈민
     * @since 1.0.0
     */
    @PostMapping("/mygg/member/delivery-address")
    public ModelAndView createDeliveryAddress(
            @ModelAttribute(name = "addressRequest") @Valid final DeliveryAddressCreateRequest addressRequest,
            BindingResult bindingResult)
            throws UnAuthenticException, UnAuthorizationException {

        if (bindingResult.hasErrors()) {
            return new ModelAndView(PAGES + MYGG + MEMBER + DELIVERY_ADDRESSES + FORM);
        }

        memberService.createDeliveryAddress(addressRequest);

        return new ModelAndView(REDIRECT + MYGG + MEMBER + DELIVERY_ADDRESSES);
    }

    /**
     * 회원이 가진 배송지 정보를 삭제하는 DeleteMapping 메소드 입니다.
     *
     * @param deliveryAddressId - 삭제하는 배송지의 식별번호
     * @author 김훈민
     * @since 1.0.0
     */

    @DeleteMapping("mygg/member/delivery-address/{deliveryAddressId}")
    public ModelAndView deleteDeliveryAddress(@PathVariable @Min(1) final Long deliveryAddressId)
            throws UnAuthenticException, UnAuthorizationException {

        memberService.deleteDeliveryAddress(deliveryAddressId);

        return new ModelAndView(REDIRECT + MYGG + MEMBER + DELIVERY_ADDRESSES);
    }

}
