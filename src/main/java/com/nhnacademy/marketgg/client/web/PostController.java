package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.client.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/shop/v1/customer-services")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private static final String DEFAULT_POST = "/shop/v1/customer-services";

    @GetMapping("/{type}")
    public ModelAndView index(@PathVariable String type, @RequestParam @DefaultValue(value = "0") final Integer page) throws JsonProcessingException {
        ModelAndView mav = new ModelAndView("board/" + type + "/index");
        List<PostResponse> responses = postService.retrievesPosts(PageRequest.of(page, 10), type);
        mav.addObject("type", type);
        mav.addObject("page", page);
        mav.addObject("isEnd", this.checkPageEnd(responses));
        mav.addObject("responses", responses);
        mav.addObject("searchType", "no");

        return mav;
    }

    @GetMapping("/{type}/create")
    public ModelAndView doCreatePost(@PathVariable String type) {
        return new ModelAndView("board/" + type + "/input-form");
    }

    @PostMapping("/{type}/create")
    public ModelAndView createPost(@PathVariable String type,
                                   @ModelAttribute final PostRequest postRequest) throws JsonProcessingException {

        ModelAndView mav = new ModelAndView("redirect:" + DEFAULT_POST + "/" + type);
        postService.createPost(postRequest, type);

        return mav;
    }

    @GetMapping("/{type}/{boardNo}/retrieve")
    public ModelAndView retrievePost(@PathVariable final String type, @PathVariable final Long boardNo) {

        ModelAndView mav = new ModelAndView("board/" + type + "/detail");

        if (type.compareTo("oto-inquiries") == 0) {
            PostResponseForOtoInquiry response = postService.retrievePostForOtoInquiry(boardNo, type);
            mav.addObject("response", response);
            mav.addObject("isEnd", this.checkPageEnd(response.getCommentList()));
        } else {
            mav.addObject("response", postService.retrievePost(boardNo, type));
        }
        mav.addObject("type", type);

        return mav;
    }

    @GetMapping("/{type}/{boardNo}/update")
    public ModelAndView doUpdatePost(@PathVariable final String type, @PathVariable final Long boardNo) {
        ModelAndView mav = new ModelAndView("board/" + type + "/input-form");
        mav.addObject("response", postService.retrievePost(boardNo, type));

        return mav;
    }

    @PutMapping("/{type}/{boardNo}/update")
    public ModelAndView updatePost(@PathVariable final String type, @PathVariable final Long boardNo,
                                   @ModelAttribute final PostRequest postRequest) {

        ModelAndView mav = new ModelAndView("redirect:" + DEFAULT_POST + "/" + type);
        postService.updatePost(boardNo, postRequest, type);

        return mav;
    }

    @DeleteMapping("/{type}/{boardNo}/delete")
    public ModelAndView deletePost(@PathVariable final String type,
                                   @PathVariable final Long boardNo) {

        ModelAndView mav = new ModelAndView("redirect:" + DEFAULT_POST + "/" + type);
        postService.deletePost(boardNo, type);

        return mav;
    }

    private <T> Integer checkPageEnd(List<T> list) {
        if (list.size() < 11) {
            return 1;
        }
        return 0;
    }

}
