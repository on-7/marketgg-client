package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.service.DibService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 찜 Controller 입니다.
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/shop/v1/members/{memberId}/dibs")
@RequiredArgsConstructor
public class DibController {

    private final DibService dibService;

    /**
     * 지정한 회원의 찜 목록을 조회하는 Mapping 을 지원합니다.
     *
     * @param memberId - 지정한 회원의 식별번호입니다.
     * @return 자신의 찜 목록 페이지로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping
    public ModelAndView retrieveDibs(@PathVariable final Long memberId) {
        ModelAndView mav = new ModelAndView("/mygg/dibs/index");
        mav.addObject("dibs", dibService.retrieveDibs(memberId));
        mav.addObject("id", memberId);

        return mav;
    }

    /**
     * 지정한 상품을 지정한 회원의 찜 목록에서 삭제하는 Mapping 을 지원합니다.
     *
     * @param memberId  - 지정한 회원의 식별번호입니다.
     * @param productId - 지정한 상품의 식별번호입니다.
     * @return 다시 상품 조회 페이지로 이동합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/{productId}")
    public ModelAndView deleteDib(@PathVariable final Long memberId,
                                  @PathVariable final Long productId) {

        dibService.deleteDib(memberId, productId);
        return new ModelAndView("redirect:/shop/v1/members/" + memberId + "/dibs/");
    }

}
