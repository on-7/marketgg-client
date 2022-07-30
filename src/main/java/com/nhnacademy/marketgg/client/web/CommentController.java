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

@Controller
@RequestMapping("/shop/v1/customer-services/oto-inquiries")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private static final String DEFAULT_POST = "/shop/v1/customer-services";

    @PostMapping("/{csPostNo}/members/{memberId}")
    public ModelAndView createComment(@PathVariable final Long csPostNo, @PathVariable final Long memberId,
                                      @ModelAttribute final CommentRequest commentRequest) throws JsonProcessingException {

        ModelAndView mav = new ModelAndView("redirect:" + DEFAULT_POST + "/oto-inquiries/" + csPostNo + "/retrieve");
        commentService.createComment(csPostNo, memberId, commentRequest);

        return mav;
    }

}
