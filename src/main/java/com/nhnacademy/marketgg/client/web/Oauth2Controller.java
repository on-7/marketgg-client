package com.nhnacademy.marketgg.client.web;

import static com.nhnacademy.marketgg.client.util.GgUrlUtils.WEEK_SECOND;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.oauth2.GoogleProfile;
import com.nhnacademy.marketgg.client.service.Oauth2Service;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Google 로그인을 진행하는 Controller.
 *
 * @author 윤동열
 */
@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class Oauth2Controller {

    private final Oauth2Service oAuth2Service;

    @GetMapping("/google")
    public RedirectView googleLogin() {

        return new RedirectView(oAuth2Service.getRedirectUrl());
    }

    @GetMapping("/oauth2/code/google")
    public ModelAndView googleRedirect(@RequestParam String code, HttpSession session, HttpServletResponse response,
                                       RedirectAttributes redirectAttributes) throws JsonProcessingException {

        String sessionId = session.getId();

        Optional<GoogleProfile> googleProfile = oAuth2Service.attemptLogin(code, sessionId);

        if (googleProfile.isPresent()) {
            redirectAttributes.addFlashAttribute("profile", googleProfile.get());
            return new ModelAndView("redirect:/signup");  // 회원가입 폼
        }

        Cookie cookie = new Cookie(JwtInfo.SESSION_ID, sessionId);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(WEEK_SECOND);  // 1 주일
        cookie.setPath("/");
        response.addCookie(cookie);

        return new ModelAndView("redirect:/");
    }

}
