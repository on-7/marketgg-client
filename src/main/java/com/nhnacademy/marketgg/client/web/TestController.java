package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.annotation.Member;
import com.nhnacademy.marketgg.client.dto.MemberInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    @GetMapping("/test1")
    public ModelAndView test(@Member MemberInfo member) {
        System.out.println(member);
        return new ModelAndView("index");
    }

}
