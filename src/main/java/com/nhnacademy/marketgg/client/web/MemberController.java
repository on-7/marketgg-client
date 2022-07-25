package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.dto.Alert;
import com.nhnacademy.marketgg.client.dto.request.MemberSignupRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberUpdateToAuth;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 회원관리에 관련된 Controller 입니다.
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/shop/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private static final String DEFAULT_MEMBER = "/shop/v1/members";

    /**
     * 선택한 회원의 GG 패스 화면으로 이동합니다.
     *
     * @param memberId - GG 패스 페이지를 조회할 회원의 식별번호입니다.
     * @return 회원의 식별번호를 담고, GG 패스의 index 페이지로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/{memberId}/ggpass")
    public ModelAndView index(@PathVariable final Long memberId) {
        ModelAndView mav = new ModelAndView("/ggpass/index");
        mav.addObject("id", memberId);
        mav.addObject("time", LocalDate.from(memberService.retrievePassUpdatedAt(memberId)));

        return mav;
    }

    /**
     * 선택한 회원을 GG 패스에 구독시키는 Mapping 을 지원합니다.
     *
     * @param memberId - GG 패스를 구독할 회원의 식별번호입니다.
     * @return 갱신일자가 지금 시간보다 늦을 시, ERROR 메세지와 함께 다시 Index 페이지로 이동합니다.
     * 갱신일자가 지금 시간보다 빠를 시, 구독하는 메소드를 실행한 후, 다시 Index 페이지로 이동합니다.
     * @since 1.0.0
     */
    @PostMapping("/{memberId}/ggpass/subscribe")
    public ModelAndView subscribePass(@PathVariable final Long memberId) {
        if (memberService.retrievePassUpdatedAt(memberId).isAfter(LocalDateTime.now())) {
            ModelAndView mav = new ModelAndView("message");
            mav.addObject("message", new Alert("이미 구독하신 상태입니다.",
                    DEFAULT_MEMBER + "/" + memberId + "/ggpass"));
            return mav;
        }
        memberService.subscribePass(memberId);

        return new ModelAndView("redirect:" + DEFAULT_MEMBER + "/" + memberId + "/ggpass");
    }

    /**
     * 선택한 회원을 GG 패스에 구독해지시키는 Mapping 을 지원합니다.
     *
     * @param memberId - GG 패스를 구독해지할 회원의 식별번호입니다.
     * @return GG 패스 구독을 해지하는 메소드 실행 후, 다시 Index 페이지로 이동합니다.
     * @since 1.0.0
     */
    @PostMapping("/{memberId}/ggpass/withdraw")
    public ModelAndView withdrawPass(@PathVariable final Long memberId) {
        memberService.withdrawPass(memberId);

        return new ModelAndView("redirect:" + DEFAULT_MEMBER + "/" + memberId + "/ggpass");
    }

    /**
     * 회원가입 View
     *
     * @return 회원가입 폼
     * @since 1.0.0
     */
    @GetMapping("/signup")
    public ModelAndView signup() {
        return new ModelAndView("member/signup");
    }

    /**
     * 회원가입 요청을 받아 회원가입을 진행합니다.
     *
     * @param memberSignupRequest - 회원가입에 필요한 요청 정보 객체입니다.
     * @return 회원가입 실행 후, 다시 Index 페이지로 이동합니다.
     * @since 1.0.0
     */
    @PostMapping("/signup")
    public ModelAndView doSignup(final MemberSignupRequest memberSignupRequest) {

        memberService.doSignup(memberSignupRequest);
        return new ModelAndView("index");
    }

    /**
     * 회원정보 수정 요청을 받아 회원정보 수정 프로세스를 진행합니다.
     *
     * @param memberUpdateToAuth - 회원정보 수정에 필요한 요청 정보 객체 (Auth 정보만 수정됨)  입니다.
     * @param session            - session 이 없는 회원의 접근을 막기위한 파라미터 입니다.
     * @return 회원수정 실행 후, 다시 Index 페이지로 이동합니다.
     * @since 1.0.0
     */
    @PostMapping("/update")
    public ModelAndView doUpdate(@ModelAttribute MemberUpdateToAuth memberUpdateToAuth, HttpSession session) {
        String sessionId = (String) session.getAttribute(JwtInfo.SESSION_ID);

        if (Objects.isNull(sessionId)) {
            // TODO: Merge 후 처리할 예정.
            throw new RuntimeException();
        }
        memberService.update(memberUpdateToAuth, sessionId);
        return new ModelAndView("index");
    }

    /**
     * 회원탈퇴 요청을 받아 회원탈퇴 프로세스를 진행합니다.
     *
     * @param session - session 이 없는 회원의 접근을 막기위한 파라미터 입니다.
     * @return 회원탈퇴 실행 후, 다시 Index 페이지로 이동합니다.
     */
    @GetMapping("/withdraw")
    public ModelAndView doWithdraw(HttpSession session) {
        String sessionId = (String) session.getAttribute(JwtInfo.SESSION_ID);

        if (Objects.isNull(sessionId)) {
            // TODO: Merge 후 처리할 예정.
            throw new RuntimeException();
        }

        memberService.withdraw(sessionId);
        return new ModelAndView("index");
    }

}
