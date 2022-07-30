package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.service.OAuth2Service;
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
    public ModelAndView googleRedirect(@RequestParam String code) {
        String name = oAuth2Service.getToken(code);
        return new ModelAndView("index").addObject("name", name);
    }

}
