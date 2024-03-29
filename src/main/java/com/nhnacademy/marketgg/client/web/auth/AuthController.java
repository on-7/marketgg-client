package com.nhnacademy.marketgg.client.web.auth;

import static com.nhnacademy.marketgg.client.util.GgUtils.REDIRECT_TO_INDEX;
import static com.nhnacademy.marketgg.client.util.GgUtils.WEEK_SECOND;

import com.nhnacademy.marketgg.client.annotation.NoAuth;
import com.nhnacademy.marketgg.client.dto.member.LoginRequest;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.jwt.Role;
import com.nhnacademy.marketgg.client.service.auth.AuthService;
import com.nhnacademy.marketgg.client.util.GgUtils;
import com.nhnacademy.marketgg.client.util.LoginStatus;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 인증 관련 처리를 진행하는 컨트롤러입니다.
 *
 * @author 윤동열
 * @version 1.0.0
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 로그인 페이지 요청 시 실행됩니다.
     *
     * @return - 로그인 페이지
     */
    @NoAuth
    @GetMapping("/login")
    public ModelAndView login(@ModelAttribute LoginRequest loginRequest, HttpServletRequest request) {
        log.error("Referer: {}", request.getHeader("Referer"));
        if (!GgUtils.hasRole(SecurityContextHolder.getContext().getAuthentication(), Role.ROLE_ANONYMOUS)) {
            String referer = request.getHeader("Referer");
            if (Objects.nonNull(referer) && referer.startsWith("http://localhost:5050")) {
                return new ModelAndView(REDIRECT_TO_INDEX + referer);
            } else {
                return new ModelAndView(REDIRECT_TO_INDEX);
            }
        }

        return new ModelAndView("pages/members/login");
    }

    /**
     * 사용자의 로그인 요청을 처리합니다.
     *
     * @param loginRequest  - 사용자가 입력한 이메일, 비밀번호
     * @param bindingResult - 유효성 결과
     * @param response      - 쿠키를 추가하기 위해 입력받음
     * @param httpSession   - 첫 로그인 시 세션 아이디 정보를 얻기위해 입력받음
     * @return 메인페이지로 리다이렉트합니다.
     */
    @NoAuth
    @PostMapping("/login")
    public ModelAndView doLogin(@ModelAttribute @Valid LoginRequest loginRequest,
                                BindingResult bindingResult, HttpServletResponse response,
                                HttpSession httpSession, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("pages/members/login");
        }

        LoginStatus loginStatus = authService.doLogin(loginRequest, httpSession.getId());

        if (loginStatus == LoginStatus.UNAUTHORIZED) {
            redirectAttributes.addFlashAttribute("loginStatus", LoginStatus.UNAUTHORIZED);
            return new ModelAndView("redirect:/login");
        } else if (loginStatus == LoginStatus.WITHDRAW) {
            redirectAttributes.addFlashAttribute("loginStatus", LoginStatus.WITHDRAW);
            return new ModelAndView("redirect:/login");
        }

        Cookie cookie = new Cookie(JwtInfo.SESSION_ID, httpSession.getId());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(WEEK_SECOND); // 일주일을 초단위로 나타냄
        cookie.setPath("/");

        response.addCookie(cookie);

        return new ModelAndView(REDIRECT_TO_INDEX);
    }

    /**
     * 로그아웃을 진행합니다.
     *
     * @return 메인페이지로 리다이렉트합니다.
     */
    @GetMapping("/logout")
    public ModelAndView logout(RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!GgUtils.hasRole(authentication, Role.ROLE_ANONYMOUS)) {
            authService.logout();
        }

        redirectAttributes.addFlashAttribute("logout", true);
        return new ModelAndView(REDIRECT_TO_INDEX);
    }

}
