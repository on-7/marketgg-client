package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CommentRequest;
import com.nhnacademy.marketgg.client.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 1:1 문의의 답글에 관련된 Controller 입니다.
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/customer-services/oto-inquiries")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private static final String DEFAULT_POST = "/shop/v1/customer-services";

    /**
     * 입력한 정보로 1:1 문의의 답변을 등록하는 메소드를 실행하고 다시 1:1 문의 페이지로 이동합니다.
     *
     * @param csPostNo - 답변을 작성할 1:1문의의 식별번호입니다.
     * @param memberId - 답변을 작성하는 회원의 번호입니다.
     * @param commentRequest - 답변의 정보를 담은 객체입니다.
     * @return 답변을 등록하는 메소드를 실행하고 다시 1:1 문의 페이지로 이동합니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    @PostMapping("/{csPostNo}/members/{memberId}")
    public ModelAndView createComment(@PathVariable final Long csPostNo, @PathVariable final Long memberId,
                                      @ModelAttribute final CommentRequest commentRequest) throws JsonProcessingException {

        ModelAndView mav = new ModelAndView("redirect:" + DEFAULT_POST + "/oto-inquiries/" + csPostNo + "/retrieve");
        commentService.createComment(csPostNo, memberId, commentRequest);

        return mav;
    }

}
