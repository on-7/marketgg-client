package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
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

/**
 * 고객센터와 관련된 Controller 입니다.
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/shop/v1/admin/customer-services")
@RequiredArgsConstructor
public class AdminPostController {

    private final PostService postService;

    private static final String DEFAULT_ADMIN_POST = "/shop/v1/admin/customer-services";
    private static final Integer PAGE_SIZE = 10;

    /**
     * 고객센터의 게시판 타입에 맞는 게시글 목록을 보여주는 페이지입니다.
     *
     * @param type - 보여줄 고객센터의 게시판의 타입입니다.
     * @param page - 보여줄 게시글 목록의 페이지 번호입니다.
     * @return 고객센터의 타입에 맞는 게시글 목록을 보여주는 페이지로 이동합니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    @GetMapping("/{type}")
    public ModelAndView index(@PathVariable String type, @RequestParam @DefaultValue(value = "0") final Integer page) throws JsonProcessingException {
        ModelAndView mav = new ModelAndView("board/" + type + "/index");
        List<PostResponse> responses = postService.retrievesPostList(page, type);
        mav.addObject("type", type);
        mav.addObject("page", page);
        mav.addObject("isEnd", this.checkPageEnd(responses));
        mav.addObject("responses", responses);
        mav.addObject("searchType", "no");
        mav.addObject("isAdmin", "yes");
        mav.addObject("reasons", postService.retrieveOtoReason());

        return mav;
    }

    /**
     * 고객센터의 타입에 맞는 게시글 등록을 할 수 있는 페이지로 이동합니다.
     *
     * @param type - 등록을 진행할 고객센터의 게시판의 타입입니다.
     * @return 고객센터의 타입에 맞는 게시글 등록을 할 수 있는 페이지로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/{type}/create")
    public ModelAndView doCreatePost(@PathVariable String type) {
        return new ModelAndView("board/" + type + "/create-form");
    }

    /**
     * 1:1문의글을 등록 후 다시 1:1문의 목록으로 이동합니다.
     *
     * @param type - 등록을 진행할 고객센터 게시판의 타입입니다.
     * @param postRequest - 등록할 게시글의 정보를 담은 객체입니다.
     * @return 해당 정보로 게시글을 등록 후 다시 Index 페이지로 이동합니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    @PostMapping("/{type}/create")
    public ModelAndView createPost(@PathVariable String type,
                                   @ModelAttribute final PostRequest postRequest) throws JsonProcessingException {

        ModelAndView mav = new ModelAndView("redirect:" + DEFAULT_ADMIN_POST + "/" + type);
        postService.createPost(postRequest, type);

        return mav;
    }

    /**
     * 고객센터의 타입에 맞는 게시글을 수정할 수 있는 페이지로 이동합니다.
     *
     * @param type - 수정할 게시글의 게시판 타입입니다.
     * @param boardNo - 수정할 게시글의 식별번호입니다.
     * @return 지정한 게시글을 수정할 수 있는 페이지로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/{type}/{boardNo}/update")
    public ModelAndView doUpdatePost(@PathVariable final String type, @PathVariable final Long boardNo) {
        ModelAndView mav = new ModelAndView("board/" + type + "/update-form");
        mav.addObject("response", postService.retrievePost(boardNo, type));

        return mav;
    }

    /**
     * 받은 정보로 타입에 맞는 게시글을 수정 후 다시 게시글 목록으로 이동합니다.
     *
     * @param type - 수정할 게시글의 게시판 타입입니다.
     * @param boardNo - 수정할 게시글의 식별번호입니다.
     * @param postRequest - 수정할 정보를 담은 객체입니다.
     * @return 게시글을 수정한 후, 다시 게시글 목록 페이지로 이동합니다.
     * @since 1.0.0
     */
    @PutMapping("/{type}/{boardNo}/update")
    public ModelAndView updatePost(@PathVariable final String type, @PathVariable final Long boardNo,
                                   @ModelAttribute final PostRequest postRequest) {

        ModelAndView mav = new ModelAndView("redirect:" + DEFAULT_ADMIN_POST + "/" + type);
        postService.updatePost(boardNo, postRequest, type);

        return mav;
    }

    /**
     * 지정한 게시글을 삭제 한 후 다시 게시글 목록으로 이동합니다.
     *
     * @param type - 삭제할 게시글의 게시판 타입입니다.
     * @param boardNo - 삭제할 게시글의 식별번호입니다.
     * @return 게시글을 삭제한 후, 다시 게시글 목록 페이지로 이동합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/{type}/{boardNo}/delete")
    public ModelAndView deletePost(@PathVariable final String type,
                                   @PathVariable final Long boardNo) {

        ModelAndView mav = new ModelAndView("redirect:" + DEFAULT_ADMIN_POST + "/" + type);
        postService.deletePost(boardNo, type);

        return mav;
    }

    private <T> Integer checkPageEnd(List<T> list) {
        if (list.size() <= PAGE_SIZE) {
            return 1;
        }
        return 0;
    }

}
