package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.SearchBoardResponse;
import com.nhnacademy.marketgg.client.exception.NotFoundException;
import com.nhnacademy.marketgg.client.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

/**
 * 고객센터와 관련된 Controller 입니다.
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/customer-services")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private static final String DEFAULT_POST = "/customer-services";
    private static final Integer PAGE_SIZE = 10;
    private static final String USER = "user";

    /**
     * 고객센터의 게시판 타입에 맞는 게시글 목록을 보여주는 페이지입니다.
     *
     * @param type - 보여줄 고객센터의 게시판의 타입입니다.
     * @param page - 보여줄 게시글 목록의 페이지 번호입니다.
     * @return 고객센터의 타입에 맞는 게시글 목록을 보여주는 페이지로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/{type}")
    public ModelAndView index(@PathVariable final String type,
                              @RequestParam @DefaultValue(value = "0") final Integer page) {

        ModelAndView mav = new ModelAndView("board/" + type + "/index");
        List<PostResponse> responses;
        if (type.compareTo("oto-inquiries") == 0) {
            responses = postService.retrievesPostListForMe(page, type);
        } else {
            responses = postService.retrievesPostList(page, this.checkTypeToCategoryCode(type), USER);
        }
        mav.addObject("page", page);
        mav.addObject("isEnd", this.checkPageEnd(responses));
        mav.addObject("responses", responses);
        mav.addObject("searchType", "no");
        mav.addObject("isAdmin", "no");
        mav.addObject("reasons", postService.retrieveOtoReason());

        return mav;
    }

    /**
     * 고객센터의 1:1문의 게시글 등록을 할 수 있는 페이지로 이동합니다.
     *
     * @return 고객센터의 1:1문의 게시글 등록을 할 수 있는 페이지로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/oto-inquiries/create")
    public ModelAndView doCreatePost() {
        ModelAndView mav = new ModelAndView("board/oto-inquiries/create-form");
        mav.addObject("reasons", postService.retrieveOtoReason());
        return mav;
    }

    /**
     * 1:1문의글을 등록 후 다시 1:1문의 목록으로 이동합니다.
     *
     * @param type        - 등록을 진행할 고객센터 게시판의 타입입니다.
     * @param postRequest - 등록할 게시글의 정보를 담은 객체입니다.
     * @return 해당 정보로 게시글을 등록 후 다시 Index 페이지로 이동합니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    @PostMapping("/{type}/create")
    public ModelAndView createPost(@PathVariable String type,
                                   @ModelAttribute final PostRequest postRequest) throws JsonProcessingException {

        ModelAndView mav = new ModelAndView("redirect:" + DEFAULT_POST + "/" + type);
        postService.createPost(postRequest, USER);

        return mav;
    }

    /**
     * 선택한 게시글의 상세 정보를 조회 할 수 있습니다.
     *
     * @param type    - 조회를 진행할 고객센터 게시판의 타입입니다.
     * @param boardNo - 조회를 진행할 게시글의 식별번호입니다.
     * @return 지정한 식별번호의 게시글 상세조회 페이지로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/{type}/{boardNo}/retrieve")
    public ModelAndView retrievePost(@PathVariable final String type, @PathVariable final Long boardNo) {

        ModelAndView mav = new ModelAndView("board/" + type + "/detail");

        if (type.compareTo("oto-inquiries") == 0) {
            mav.addObject("response", postService.retrievePostForOtoInquiry(boardNo, type, USER));
        } else {
            mav.addObject("response", postService.retrievePost(boardNo, type, USER));
        }

        return mav;
    }

    /**
     * 지정한 카테고리에서 게시판을 검색합니다.
     *
     * @param categoryCode - 검색을 진행할 게시판의 타입입니다.
     * @param keyword      - 검색을 진행할 검색어입니다.
     * @param pageable     - 조회할 페이지의 페이지 정보입니다.
     * @return 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 과 관련한 파싱 예외처리입니다.
     * @since 1.0.0
     */
    @PostMapping("/search/categories/{categoryCode}")
    public ModelAndView searchForCategory(@PathVariable final String categoryCode,
                                          @RequestParam final String keyword, final Pageable pageable)
            throws JsonProcessingException {

        SearchRequest request =
                new SearchRequest(keyword, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        ModelAndView mav = new ModelAndView("board/" + this.checkCategoryCodeToType(categoryCode) + "/index");
        List<SearchBoardResponse> responses = postService.searchForCategory(categoryCode, request, USER);
        mav.addObject("page", pageable.getPageNumber());
        mav.addObject("isEnd", this.checkPageEnd(responses));
        mav.addObject("responses", responses);
        mav.addObject("searchType", "default");
        mav.addObject("isAdmin", "no");
        mav.addObject("keyword", keyword);
        return mav;
    }

    /**
     * 지정한 카테고리의 게시판에서 사유를 지정해 검색합니다.
     *
     * @param categoryCode - 검색을 진행할 게시판의 타입입니다.
     * @param keyword      - 검색을 진행할 검색어입니다.
     * @param pageable     - 조회할 페이지의 페이지 정보입니다.
     * @param reason       - 지정한 사유의 값입니다.
     * @return 지정한 상태내의 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 과 관련한 파싱 예외처리입니다.
     * @since 1.0.0
     */
    @PostMapping("/search/categories/{categoryCode}/reason")
    public ModelAndView searchForReason(@PathVariable final String categoryCode, @RequestParam final String keyword,
                                        final Pageable pageable, @RequestParam final String reason)
            throws JsonProcessingException {

        SearchRequest request =
                new SearchRequest(keyword, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        ModelAndView mav = new ModelAndView("board/" + this.checkCategoryCodeToType(categoryCode) + "/index");
        List<SearchBoardResponse> responses;
        responses = postService.searchForReason(categoryCode, request, reason, USER);
        mav.addObject("page", pageable.getPageNumber());
        mav.addObject("isEnd", this.checkPageEnd(responses));
        mav.addObject("responses", responses);
        mav.addObject("searchType", "reason");
        mav.addObject("isAdmin", "no");
        mav.addObject("reason", reason);
        mav.addObject("keyword", keyword);
        return mav;
    }

    /**
     * 지정한 카테고리의 게시판에서 상태를 지정해 검색합니다.
     *
     * @param categoryCode - 검색을 진행할 게시판의 타입입니다.
     * @param keyword      - 검색을 진행할 검색어입니다.
     * @param pageable     - 조회할 페이지의 페이지 정보입니다.
     * @param status       - 지정한 상태의 값입니다.
     * @return 지정한 상태내의 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 과 관련한 파싱 예외처리입니다.
     * @since 1.0.0
     */
    @PostMapping("/search/categories/{categoryCode}/status")
    public ModelAndView searchForStatus(@PathVariable final String categoryCode, @RequestParam final String keyword,
                                        final Pageable pageable, @RequestParam final String status)
            throws JsonProcessingException {

        SearchRequest request =
                new SearchRequest(keyword, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

        ModelAndView mav = new ModelAndView("board/" + this.checkCategoryCodeToType(categoryCode) + "/index");
        List<SearchBoardResponse> responses;
        responses = postService.searchForStatus(categoryCode, request, status, USER);
        mav.addObject("page", pageable.getPageNumber());
        mav.addObject("isEnd", this.checkPageEnd(responses));
        mav.addObject("responses", responses);
        mav.addObject("searchType", "status");
        mav.addObject("isAdmin", "no");
        mav.addObject("status", status);
        mav.addObject("keyword", keyword);
        return mav;
    }

    /**
     * 고객센터의 1:1문의 게시글을 수정할 수 있는 페이지로 이동합니다.
     *
     * @param boardNo - 수정할 게시글의 식별번호입니다.
     * @return 지정한 게시글을 수정할 수 있는 페이지로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/oto-inquiries/{boardNo}/update")
    public ModelAndView doUpdatePost(@PathVariable final Long boardNo) {
        ModelAndView mav = new ModelAndView("board/oto-inquiries/update-form");
        mav.addObject("response", postService.retrievePost(boardNo, "oto-inquiries", USER));
        mav.addObject("reasons", postService.retrieveOtoReason());

        return mav;
    }

    /**
     * 받은 정보로 타입에 맞는 게시글을 수정 후 다시 게시글 목록으로 이동합니다.
     *
     * @param boardNo     - 수정할 게시글의 식별번호입니다.
     * @param postRequest - 수정할 정보를 담은 객체입니다.
     * @return 게시글을 수정한 후, 다시 게시글 목록 페이지로 이동합니다.
     * @since 1.0.0
     */
    @PutMapping("/oto-inquiries/{boardNo}/update")
    public ModelAndView updatePost(@PathVariable final Long boardNo, @ModelAttribute final PostRequest postRequest) {

        ModelAndView mav = new ModelAndView("redirect:" + DEFAULT_POST + "/oto-inquiries");
        postService.updatePost(boardNo, postRequest, "oto-inquiries", USER);

        return mav;
    }

    /**
     * 1:1문의 게시글을 삭제 한 후 다시 게시글 목록으로 이동합니다.
     *
     * @param boardNo - 삭제할 게시글의 식별번호입니다.
     * @return 게시글을 삭제한 후, 다시 게시글 목록 페이지로 이동합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/oto-inquiries/{boardNo}/delete")
    public ModelAndView deletePost(@PathVariable final Long boardNo) {
        ModelAndView mav = new ModelAndView("redirect:" + DEFAULT_POST + "/oto-inquiries");
        postService.deletePost(boardNo, "oto-inquiries", USER);

        return mav;
    }

    private String checkCategoryCodeToType(final String categoryCode) {
        switch (categoryCode) {
            case "701": {
                return "notices";
            }
            case "702": {
                return "oto-inquiries";
            }
            case "703": {
                return "faqs";
            }
            default: {
                throw new NotFoundException("카테고리 분류를 찾을 수 없습니다.");
            }
        }
    }

    private String checkTypeToCategoryCode(final String type) {
        if(type.compareTo("notices") == 0) {
            return "701";
        }
        return "703";
    }

    private <T> Integer checkPageEnd(List<T> list) {
        if (list.size() <= PAGE_SIZE) {
            return 1;
        }
        return 0;
    }

}
