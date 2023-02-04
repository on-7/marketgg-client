package com.nhnacademy.marketgg.client.web.customer_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.customer_service.CommentRequest;
import com.nhnacademy.marketgg.client.dto.customer_service.PostRequest;
import com.nhnacademy.marketgg.client.dto.customer_service.PostResponse;
import com.nhnacademy.marketgg.client.dto.search.SearchRequestForCategory;
import com.nhnacademy.marketgg.client.exception.NotFoundException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.service.customer_service.PostService;
import com.nhnacademy.marketgg.client.web.product.BaseController;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 고객센터와 관련된 Controller 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@Controller
@RequestMapping("/customer-services")
@RequiredArgsConstructor
public class CsPostController extends BaseController {

    private final PostService postService;

    private static final String DEFAULT_POST = "/customer-services";
    private static final String BOARD = "pages/board/";
    private static final String NOTICE_CODE = "701";
    private static final String OTO_CODE = "702";
    private static final String FAQ_CODE = "703";
    private static final Integer PAGE_SIZE = 10;

    @ModelAttribute(name = "isAdmin")
    public String isAdmin() {
        return "no";
    }

    /**
     * 게시판 타입에 맞는 게시글 목록을 보여주는 페이지입니다.
     *
     * @param categoryId - 조회할 게시판의 카테고리 식별번호입니다.
     * @param page       - 보여줄 게시글 목록의 페이지 번호입니다.
     * @return 게시판 타입에 맞는 게시글 목록을 보여주는 페이지로 이동합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryId}")
    public ModelAndView index(@PathVariable final String categoryId, @RequestParam(value = "page",
                                                                                   defaultValue = "0") final Integer page)
        throws UnAuthenticException, UnAuthorizationException {
        ModelAndView mav = new ModelAndView(String.format("pages/board/%s/index", this.convertToType(categoryId)));
        List<PostResponse> responses = postService.retrievePostList(categoryId, page);

        mav.addObject("page", page);
        mav.addObject("isEnd", this.checkPageEnd(responses));
        mav.addObject("responses", responses);
        mav.addObject("searchType", "no");
        mav.addObject("reasons", postService.retrieveOtoReason());
        mav.addObject("statusList", postService.retrieveOtoStatus());
        mav.addObject("NOTICE_CODE", NOTICE_CODE);
        mav.addObject("OTO_CODE", OTO_CODE);
        mav.addObject("FAQ_CODE", FAQ_CODE);

        return mav;
    }

    /**
     * 1:1 문의 게시글 등록을 할 수 있는 페이지로 이동합니다.
     *
     * @return 1:1 문의 게시글 등록을 할 수 있는 페이지로 이동합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/" + OTO_CODE + "/create")
    public ModelAndView doCreatePost(@ModelAttribute final PostRequest postRequest)
        throws UnAuthenticException, UnAuthorizationException {
        ModelAndView mav = new ModelAndView(BOARD + this.convertToType(OTO_CODE) + "/create-form");
        mav.addObject("reasons", postService.retrieveOtoReason());
        mav.addObject("code", OTO_CODE);
        return mav;
    }

    /**
     * 게시글을 등록 후 다시 게시글의 목록으로 이동합니다.
     *
     * @param postRequest - 등록할 게시글의 정보를 담은 객체입니다.
     * @return 해당 정보로 게시글을 등록 후 다시 Index 페이지로 이동합니다.
     * @throws JsonProcessingException  Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @PostMapping("/categories/" + OTO_CODE + "/create")
    public ModelAndView createPost(@Valid @ModelAttribute final PostRequest postRequest, BindingResult bindingResult)
        throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(BOARD + this.convertToType(OTO_CODE) + "/create-form");
        }
        postService.createPost(postRequest);

        return new ModelAndView("redirect:" + DEFAULT_POST + "/categories/" + OTO_CODE + "?page=0");
    }

    /**
     * 선택한 게시글의 상세 정보를 조회 할 수 있습니다.
     *
     * @param categoryCode - 조회를 진행할 고객센터 게시판의 타입입니다.
     * @param postId       - 조회를 진행할 게시글의 식별번호입니다.
     * @return 지정한 식별번호의 게시글 상세조회 페이지로 이동합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryCode}/{postId}")
    public ModelAndView retrievePost(@PathVariable @Size(min = 1, max = 6) final String categoryCode,
                                     @PathVariable @Min(1) final Long postId, @RequestParam(value = "page",
                                                                                            defaultValue = "0") final Integer page,
                                     @ModelAttribute CommentRequest commentRequest)
        throws UnAuthenticException, UnAuthorizationException {

        ModelAndView mav = new ModelAndView(BOARD + this.convertToType(categoryCode) + "/detail");

        mav.addObject("response", postService.retrievePost(postId, categoryCode));
        mav.addObject("page", page);

        return mav;
    }

    /**
     * 지정한 카테고리에서 게시판을 검색합니다.
     *
     * @param categoryId - 검색을 진행할 게시판의 타입입니다.
     * @param keyword    - 검색을 진행할 검색어입니다.
     * @param page       - 조회할 페이지의 페이지 정보입니다.
     * @return 검색 결과 목록을 반환합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryId}/search")
    public ModelAndView searchForCategory(@PathVariable @Size(min = 1, max = 6) final String categoryId,
                                          @RequestParam @Size(min = 1, max = 30) final String keyword,
                                          @RequestParam @Min(0) final Integer page)
        throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        ModelAndView mav = new ModelAndView(BOARD + this.convertToType(categoryId) + "/index");

        SearchRequestForCategory request = new SearchRequestForCategory(categoryId, keyword, page, PAGE_SIZE);
        List<PostResponse> responses = postService.searchForCategory(request);

        mav.addObject("page", page);
        mav.addObject("isEnd", this.checkPageEnd(responses));
        mav.addObject("responses", responses);
        mav.addObject("searchType", "default");
        mav.addObject("keyword", keyword);
        mav.addObject("reasons", postService.retrieveOtoReason());
        mav.addObject("statusList", postService.retrieveOtoStatus());
        mav.addObject("NOTICE_CODE", NOTICE_CODE);
        mav.addObject("OTO_CODE", OTO_CODE);
        mav.addObject("FAQ_CODE", FAQ_CODE);

        return mav;
    }

    /**
     * 지정한 게시글을 삭제 한 후 다시 게시글 목록으로 이동합니다.
     *
     * @param postId - 삭제할 게시글의 식별번호입니다.
     * @param page   - Redirect 할 페이지 정보입니다.
     * @return 게시글을 삭제한 후, 다시 게시글 목록 페이지로 이동합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @DeleteMapping("/categories/" + OTO_CODE + "/{postId}/delete")
    public ModelAndView deletePost(@PathVariable @Min(1) final Long postId, @RequestParam @Min(0) final Integer page)
        throws UnAuthenticException, UnAuthorizationException {
        ModelAndView mav = new ModelAndView(
            "redirect:" + DEFAULT_POST + "/categories/" + OTO_CODE + "?page=" + page);
        postService.deletePost(postId, OTO_CODE);

        return mav;
    }

    private String convertToType(final String categoryId) {
        switch (categoryId) {
            case NOTICE_CODE: {
                return "notices";
            }
            case OTO_CODE: {
                return "oto-inquiries";
            }
            case FAQ_CODE: {
                return "faqs";
            }
            default: {
                throw new NotFoundException("카테고리 분류를 찾을 수 없습니다.");
            }
        }
    }

    private <T> Integer checkPageEnd(List<T> list) {
        if (list.size() <= PAGE_SIZE - 1) {
            return 1;
        }
        return 0;
    }

}
