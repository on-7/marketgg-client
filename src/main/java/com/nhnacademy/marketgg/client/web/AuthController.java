package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.annotation.NoAuth;
import com.nhnacademy.marketgg.client.context.SessionContext;
import com.nhnacademy.marketgg.client.dto.request.LoginRequest;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.service.AuthService;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 인증 관련 처리를 진행하는 컨트롤러입니다.
 *
 * @author 윤동열
 * @version 1.0.0
 */
@Controller
@RequiredArgsConstructor
public class AuthController {

    private static final String REDIRECT_TO_INDEX = "redirect:/";

    private final AuthService authService;

    /**
     * 로그인 페이지 요청 시 실행됩니다.
     *
     * @return - 로그인 페이지
     */
    @NoAuth
    @GetMapping("/login")
    public ModelAndView login(@ModelAttribute LoginRequest loginRequest) {
        return new ModelAndView("members/login");
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
                                HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("members/login");
        }

        Cookie cookie = new Cookie(JwtInfo.SESSION_ID, httpSession.getId());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24 * 7); // 일주일

        response.addCookie(cookie);

        authService.doLogin(loginRequest, httpSession.getId());

        return new ModelAndView(REDIRECT_TO_INDEX);
    }

    /**
     * 로그아웃을 진행합니다.
     *
     * @return 메인페이지로 리다이렉트합니다.
     */
    @GetMapping("/logout")
    public ModelAndView logout() {
        Optional<String> sessionId = SessionContext.get();

        if (sessionId.isEmpty()) {
            return new ModelAndView(REDIRECT_TO_INDEX);
        }

        authService.logout(sessionId.get());

        return new ModelAndView(REDIRECT_TO_INDEX);
    }

}
