package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.oauth2.GoogleProfile;
import com.nhnacademy.marketgg.client.service.OAuth2Service;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class Oauth2Controller {

    private final OAuth2Service oAuth2Service;

    @GetMapping("/google")
    public RedirectView googleLogin() {
        return new RedirectView(oAuth2Service.getRedirectUrl());
    }

    @GetMapping("/oauth2/code/google")
    public ModelAndView googleRedirect(@RequestParam String code, HttpSession httpSession)
        throws JsonProcessingException {

        Optional<GoogleProfile> token = oAuth2Service.getToken(code, (String) httpSession.getAttribute(JwtInfo.SESSION_ID));
        if (token.isPresent()) {
            return new ModelAndView("index");
        }
        return new ModelAndView();  // 회원가입 폼
    }

}
