package com.nhnacademy.marketgg.client.web.customer_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.customer_service.CommentRequest;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.service.customer_service.CommentService;
import com.nhnacademy.marketgg.client.service.customer_service.PostService;
import com.nhnacademy.marketgg.client.web.product.BaseController;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 1:1 문의의 답글에 관련된 Controller 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@Controller
@RequestMapping("/customer-services")
@RequiredArgsConstructor
public class CommentController extends BaseController {

    private final CommentService commentService;
    private final PostService postService;
    private static final String DEFAULT_POST = "/customer-services";
    private static final String OTO_CODE = "702";

    /**
     * 입력한 정보로 1:1 문의의 답변을 등록하는 메소드를 실행하고 다시 1:1 문의 페이지로 이동합니다.
     *
     * @param postId         - 답변을 작성할 1:1문의의 식별번호입니다.
     * @param commentRequest - 답변의 정보를 담은 객체입니다.
     * @param page           - 페이지 정보입니다.
     * @return 답변을 등록하는 메소드를 실행하고 다시 1:1 문의 페이지로 이동합니다.
     * @throws JsonProcessingException  Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @PostMapping("/{postId}")
    public ModelAndView createComment(@PathVariable @Min(1) final Long postId,
                                      @RequestParam @Min(0) final Integer page,
                                      @Valid @ModelAttribute final CommentRequest commentRequest,
                                      BindingResult bindingResult)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        if (bindingResult.hasErrors()) {
            ModelAndView mav = new ModelAndView("pages/board/oto-inquiries/detail");
            mav.addObject("response", postService.retrievePost(postId, OTO_CODE));
            mav.addObject("page", page);
            return mav;
        }

        commentService.createComment(postId, commentRequest);
        return new ModelAndView("redirect:" + DEFAULT_POST + "/categories/" + OTO_CODE + "/" + postId);
    }

}
