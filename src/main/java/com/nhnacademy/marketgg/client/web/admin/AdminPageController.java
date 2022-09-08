package com.nhnacademy.marketgg.client.web.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 관리자 페이지와 관련된 요청 처리를 담당합니다.
 *
 * @author 이제훈
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
@RequiredArgsConstructor
public class AdminPageController {

    @GetMapping("/admin")
    public ModelAndView index() {
        return new ModelAndView("redirect:/admin/members");
    }

}
