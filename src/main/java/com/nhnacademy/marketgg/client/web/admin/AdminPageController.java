package com.nhnacademy.marketgg.client.web.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    // 시큐리티 설정으로 인해 URI 를 gg-admin 으로 임의 설정
    @GetMapping("/gg-admin")
    public String index() {
        return "pages/admin/index";
    }

}
