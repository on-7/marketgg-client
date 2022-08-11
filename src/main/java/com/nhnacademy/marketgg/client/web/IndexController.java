package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.dto.MemberInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class IndexController {

    @GetMapping({ "/", "/index" })
    public ModelAndView index(MemberInfo memberInfo) {
        log.info("MemberInfo = {}", memberInfo);

        ModelAndView mav = new ModelAndView("index");
        
        if (!memberInfo.isNull()) {
            mav.addObject("memberInfo", memberInfo);
        }

        return mav;
    }
}
