package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.annotation.NoAuth;
import com.nhnacademy.marketgg.client.dto.request.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.DeliveryAddressUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.SignupRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.DeliveryAddressResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.service.MemberService;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private static final String DEFAULT_MEMBER = "/members";
    private static final String DEFAULT_DELIVERY_ADDRESSES = "/delivery-addresses";

    /**
     * 회원가입 View.
     *
     * @return 회원가입 폼
     * @author 김훈민
     * @since 1.0.0
     */
    @NoAuth
    @GetMapping("/signup")
    public ModelAndView signup(@ModelAttribute(name = "member") SignupRequest member) {
        return new ModelAndView("members/signup");
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
            return new ModelAndView("members/signup");
        }

        memberService.doSignup(member);
        return new ModelAndView("redirect:/");
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
    @PostMapping("/update")
    public ModelAndView doUpdate(@ModelAttribute @Valid final MemberUpdateRequest memberUpdateRequest)
        throws UnAuthenticException, UnAuthorizationException {
        memberService.update(memberUpdateRequest);
        return new ModelAndView(REDIRECT);
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
    @GetMapping("/withdraw")
    public ModelAndView doWithdraw() throws UnAuthenticException, UnAuthorizationException {
        memberService.withdraw();
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
    public ModelAndView deliveryAddresses() throws UnAuthenticException, UnAuthorizationException {
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
        @ModelAttribute @Valid final DeliveryAddressCreateRequest addressRequest)
        throws UnAuthenticException, UnAuthorizationException {

        memberService.createDeliveryAddress(addressRequest);
        return new ModelAndView(REDIRECT + DEFAULT_MEMBER + DEFAULT_DELIVERY_ADDRESSES);
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
    public ModelAndView updateDeliveryAddress(
        @ModelAttribute @Valid final DeliveryAddressUpdateRequest updateRequest)
        throws UnAuthenticException, UnAuthorizationException {

        memberService.updateDeliveryAddress(updateRequest);
        return new ModelAndView(REDIRECT + DEFAULT_MEMBER + DEFAULT_DELIVERY_ADDRESSES);
    }

    /**
     * 회원이 가진 배송지 정보를 삭제하는 DeleteMapping 메소드 입니다.
     *
     * @param deliveryAddressId - 삭제하는 배송지의 식별번호
     * @return 배송지 수정하고있는 페이지 redirect
     * @author 김훈민
     * @since 1.0.0
     */
    @DeleteMapping("/delivery-address/{deliveryAddressId}")
    public ModelAndView deleteDeliveryAddress(@PathVariable @Min(1) final Long deliveryAddressId)
        throws UnAuthenticException, UnAuthorizationException {
        memberService.deleteDeliveryAddress(deliveryAddressId);
        return new ModelAndView(REDIRECT + DEFAULT_MEMBER + DEFAULT_DELIVERY_ADDRESSES);
    }

}
