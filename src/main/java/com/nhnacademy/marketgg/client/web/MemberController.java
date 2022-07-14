package com.nhnacademy.marketgg.client.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/shop/v1/members")
@RequiredArgsConstructor
public class MemberController {

    @PostMapping("/{memberId}/ggpass/subscribe")
    public ModelAndView subscribePass(@PathVariable Long memberId) {

    }


    @PostMapping("/ggpass/withdraw/{memberId}")
    public ModelAndView withdrawPass(@PathVariable Long memberId) {

    }

}
