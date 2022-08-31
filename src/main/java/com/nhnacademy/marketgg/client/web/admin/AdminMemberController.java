package com.nhnacademy.marketgg.client.web.admin;

import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.response.AdminMemberResponse;
import com.nhnacademy.marketgg.client.paging.Pagination;
import com.nhnacademy.marketgg.client.service.member.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/_admin/members")
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping
    public ModelAndView retrieveMember(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        PageResult<AdminMemberResponse> memberResult = memberService.retrieveMembers(page);

        Pagination pagination = new Pagination(memberResult.getTotalPages(), page);
        List<AdminMemberResponse> members = memberResult.getData();

        ModelAndView mav = new ModelAndView("pages/admin/members/list");
        mav.addObject("members", members);
        mav.addObject("pages", pagination);

        return mav;
    }

}
