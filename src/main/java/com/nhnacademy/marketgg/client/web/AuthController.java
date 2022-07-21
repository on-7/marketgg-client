package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.dto.request.LoginRequest;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.service.AuthService;
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

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/users/login");
    }

    @PostMapping("/login")
    public ModelAndView doLogin(@ModelAttribute @Valid LoginRequest loginRequest,
                                BindingResult bindingResult, HttpServletResponse response,
                                HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/login");
        }

        response.addCookie(new Cookie(JwtInfo.SESSION_ID, httpSession.getId()));

        authService.doLogin(loginRequest, httpSession.getId());

        return new ModelAndView("redirect:/");
    }

}
