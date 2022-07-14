package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.dto.Message;
import com.nhnacademy.marketgg.client.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/shop/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberId}/ggpass")
    public ModelAndView index(@PathVariable final Long memberId) {
        ModelAndView mav = new ModelAndView("/ggpass/index");
        mav.addObject("id", memberId);

        return mav;
    }

    @PostMapping("/{memberId}/ggpass/subscribe")
    public ModelAndView subscribePass(@PathVariable final Long memberId) {
        if (Boolean.TRUE.equals(memberService.checkPassUpdatedAt(memberId))) {
            ModelAndView mav = new ModelAndView("message");
            mav.addObject("error", new Message("이미 구독하신 상태입니다.",
                                               "/shop/v1/members/" + memberId + "/ggpass"));
            return mav;
        }
        memberService.subscribePass(memberId);

        return new ModelAndView("redirect:/shop/v1/members/" + memberId + "/ggpass");
    }


    @PostMapping("/{memberId}/ggpass/withdraw")
    public ModelAndView withdrawPass(@PathVariable final Long memberId) {
        memberService.withdrawPass(memberId);

        return new ModelAndView("redirect:/shop/v1/members/" + memberId + "/ggpass");
    }

}
