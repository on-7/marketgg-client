package com.nhnacademy.marketgg.client.web.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.exception.NotFoundException;
import com.nhnacademy.marketgg.client.service.PostService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 고객센터의 관리자 매핑과 관련된 Controller 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@Controller
@RequestMapping("/admin/customer-services")
@RequiredArgsConstructor
public class AdminCsPostController {

    private final PostService postService;

    private static final String DEFAULT_ADMIN_POST = "/admin/customer-services";
    private static final String OTO_CODE = "702";
    private static final Integer PAGE_SIZE = 10;

    /**
     * 고객센터의 게시판 타입에 맞는 게시글 목록을 보여주는 페이지입니다.
     *
     * @param categoryCode - 보여줄 고객센터의 게시판의 카테고리 식별번호입니다.
     * @param page         - 보여줄 게시글 목록의 페이지 번호입니다.
     * @return 고객센터의 타입에 맞는 게시글 목록을 보여주는 페이지로 이동합니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryCode}")
    public ModelAndView index(@PathVariable @Size(min = 1, max = 6) final String categoryCode, @RequestParam @Min(0) final Integer page)
        throws JsonProcessingException {

        ModelAndView mav = new ModelAndView("board/" + this.convertToType(categoryCode) + "/index");
        List<PostResponse> responses = postService.retrievePostList(categoryCode, page);
        mav.addObject("page", page);
        mav.addObject("isEnd", this.checkPageEnd(responses));
        mav.addObject("responses", responses);
        mav.addObject("searchType", "no");
        mav.addObject("isAdmin", "yes");
        mav.addObject("reasons", postService.retrieveOtoReason());
        mav.addObject("statusList", postService.retrieveOtoStatus());
        mav.addObject("code", categoryCode);

        return mav;
    }

    /**
     * 게시글을 등록할 수 있는 입력양식 페이지로 이동합니다.
     *
     * @param categoryCode - 등록을 진행 할 게시글의 게시판 카테고리 식별번호입니다.
     * @return 게시글을 등록할 수 있는 페이지로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryCode}/create")
    public ModelAndView doCreate(@PathVariable @Size(min = 1, max = 6) final String categoryCode) {
        ModelAndView mav = new ModelAndView("board/" + this.convertToType(categoryCode) + "/create-form");
        mav.addObject("code", OTO_CODE);

        return mav;
    }

    /**
     * 게시글을 등록 후 다시 게시글 목록으로 이동합니다.
     *
     * @param categoryCode - 등록을 진행할 고객센터 게시판의 타입입니다.
     * @param postRequest  - 등록할 게시글의 정보를 담은 객체입니다.
     * @return 해당 정보로 게시글을 등록 후 다시 Index 페이지로 이동합니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    @PostMapping("/categories/{categoryCode}/create")
    public ModelAndView createPost(@PathVariable @Size(min = 1, max = 6) final String categoryCode,
                                   @Valid @ModelAttribute final PostRequest postRequest, BindingResult bindingResult
    ) throws JsonProcessingException {

        if(bindingResult.hasErrors()) {
            return new ModelAndView(DEFAULT_ADMIN_POST + "/categories/" + categoryCode + "/create");
        }

        ModelAndView mav = new ModelAndView(
            "redirect:" + DEFAULT_ADMIN_POST + "/categories/" + categoryCode + "?page=0");
        postService.createPost(postRequest);

        return mav;
    }

    /**
     * 지정한 카테고리에서 게시판을 검색합니다.
     *
     * @param categoryCode - 검색을 진행할 게시판의 타입입니다.
     * @param keyword      - 검색을 진행할 검색어입니다.
     * @param page         - 조회할 페이지의 페이지 정보입니다.
     * @return 검색 결과 목록을 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryCode}/search")
    public ModelAndView searchForCategory(@PathVariable @Size(min = 1, max = 6) final String categoryCode,
                                          @RequestParam @Size(min = 1, max = 30) final String keyword,
                                          @RequestParam @Min(0) final Integer page) {

        SearchRequest request = new SearchRequest(keyword, page, PAGE_SIZE);

        ModelAndView mav = new ModelAndView("board/" + this.convertToType(categoryCode) + "/index");
        List<PostResponse> responses = postService.searchForCategory(categoryCode, request);
        mav.addObject("page", page);
        mav.addObject("isEnd", this.checkPageEnd(responses));
        mav.addObject("responses", responses);
        mav.addObject("searchType", "default");
        mav.addObject("isAdmin", "yes");
        mav.addObject("keyword", keyword);
        mav.addObject("reasons", postService.retrieveOtoReason());
        mav.addObject("statusList", postService.retrieveOtoStatus());
        mav.addObject("code", categoryCode);

        return mav;
    }

    /**
     * 지정한 카테고리의 게시판에서 사유 또는 상태를 지정해 검색합니다.
     *
     * @param optionType - 검색을 진행할 옵션의 타입입니다.
     * @param keyword    - 검색을 진행할 검색어입니다.
     * @param option     - 지정한 옵션의 값입니다.
     * @param page       - 조회할 페이지의 페이지 정보입니다.
     * @return 지정한 상태내의 검색 결과 목록을 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/" + OTO_CODE + "/options/{optionType}/search")
    public ModelAndView searchForOption(@PathVariable @Min(1) final String optionType,
                                        @RequestParam @Size(min = 1, max = 30) final String keyword,
                                        @RequestParam @Min(1) final String option,
                                        @RequestParam @Min(0) final Integer page) {

        SearchRequest request = new SearchRequest(keyword, page, PAGE_SIZE);
        ModelAndView mav = new ModelAndView("board/" + this.convertToType(OTO_CODE) + "/index");
        List<PostResponse> responses = postService.searchForOption(OTO_CODE, request, optionType, option);
        mav.addObject("page", page);
        mav.addObject("isEnd", this.checkPageEnd(responses));
        mav.addObject("responses", responses);
        mav.addObject("searchType", optionType);
        mav.addObject("isAdmin", "yes");
        mav.addObject("keyword", keyword);
        mav.addObject("reasons", postService.retrieveOtoReason());
        mav.addObject("statusList", postService.retrieveOtoStatus());
        mav.addObject("code", OTO_CODE);
        mav.addObject(optionType, option);

        return mav;
    }

    /**
     * 고객센터의 타입에 맞는 게시글을 수정할 수 있는 페이지로 이동합니다.
     *
     * @param categoryCode - 수정할 게시글의 게시판 카테고리 식별번호입니다.
     * @param postNo       - 수정할 게시글의 식별번호입니다.
     * @param page         - Redirect 할 페이지 정보입니다.
     * @return 지정한 게시글을 수정할 수 있는 페이지로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryCode}/{postNo}/update")
    public ModelAndView doUpdatePost(@PathVariable @Size(min = 1, max = 6) final String categoryCode,
                                     @PathVariable @Min(1) final Long postNo,
                                     @RequestParam @Min(0) final Integer page) {

        if (categoryCode.compareTo(OTO_CODE) == 0) {
            return new ModelAndView("redirect:" + DEFAULT_ADMIN_POST + "/categories/" + OTO_CODE + "?page=" + page);
        }

        ModelAndView mav = new ModelAndView("board/" + this.convertToType(categoryCode) + "/update-form");
        mav.addObject("response", postService.retrievePost(postNo, categoryCode));
        mav.addObject("reasons", postService.retrieveOtoReason());
        mav.addObject("page", page);
        mav.addObject("code", categoryCode);

        return mav;
    }

    /**
     * 받은 정보로 타입에 맞는 게시글을 수정 후 다시 게시글 목록으로 이동합니다.
     *
     * @param categoryCode - 수정할 게시글의 게시판 카테고리 식별번호입니다.
     * @param postNo       - 수정할 게시글의 식별번호입니다.
     * @param postRequest  - 수정할 정보를 담은 객체입니다.
     * @param page         - Redirect 할 페이지 정보입니다.
     * @return 게시글을 수정한 후, 다시 게시글 목록 페이지로 이동합니다.
     * @since 1.0.0
     */
    @PutMapping("/categories/{categoryCode}/{postNo}/update")
    public ModelAndView updatePost(@PathVariable @Size(min = 1, max = 6) final String categoryCode,
                                   @PathVariable @Min(1) final Long postNo,
                                   @RequestParam @Min(0) final Integer page,
                                   @Valid @ModelAttribute final PostRequest postRequest,
                                   BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return new ModelAndView(DEFAULT_ADMIN_POST + "/categories/" + categoryCode + "/" + postNo + "/update?page=" + page);
        }

        if (categoryCode.compareTo(OTO_CODE) == 0) {
            return new ModelAndView("redirect:" + DEFAULT_ADMIN_POST + "/categories/" + OTO_CODE + "?page=" + page);
        }

        ModelAndView mav = new ModelAndView("redirect:" + DEFAULT_ADMIN_POST + "/categories/" + categoryCode + "?page=" + page);
        postService.updatePost(postNo, postRequest, categoryCode);

        return mav;
    }

    /**
     * 지정한 게시글을 삭제 한 후 다시 게시글 목록으로 이동합니다.
     *
     * @param categoryCode - 수정할 게시글의 게시판 카테고리 식별번호입니다.
     * @param postNo       - 수정할 게시글의 식별번호입니다.
     * @param page         - Redirect 할 페이지 정보입니다.
     * @return 게시글을 삭제한 후, 다시 게시글 목록 페이지로 이동합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/categories/{categoryCode}/{postNo}/delete")
    public ModelAndView deletePost(@PathVariable @Size(min = 1, max = 6) final String categoryCode,
                                   @PathVariable @Min(1) final Long postNo,
                                   @RequestParam @Min(0) final Integer page) {

        ModelAndView mav = new ModelAndView("redirect:" + DEFAULT_ADMIN_POST + "/categories/" + categoryCode + "?page=" + page);
        postService.deletePost(postNo, categoryCode);

        return mav;
    }

    /**
     * 지정한 1:1 문의의 상태를 입력해 변경 할 수 있습니다.
     *
     * @param postNo      - 수정할 게시글의 식별번호입니다.
     * @param postRequest - 게시판의 상태를 변경 할 정보를 담은 객체입니다.
     * @param page        - Redirect 할 페이지 정보입니다.
     * @return 다시 게시판의 Index 페이지로 이동합니다.
     * @throws JsonProcessingException JSON 과 관련한 파싱 예외처리입니다.
     * @since 1.0.0
     */
    @PatchMapping("/categories/" + OTO_CODE + "/{postNo}/status")
    public ModelAndView changeStatus(@PathVariable @Min(1) final Long postNo,
                                     @RequestParam @Min(0) final Integer page,
                                     @Valid @ModelAttribute final PostStatusUpdateRequest postRequest,
                                     BindingResult bindingResult)
        throws JsonProcessingException {

        if(!bindingResult.hasErrors()) {
            postService.changeStatus(postNo, postRequest);
        }

        return new ModelAndView("redirect:" + DEFAULT_ADMIN_POST + "/categories/" + OTO_CODE + "?page=" + page);
    }

    private <T> Integer checkPageEnd(final List<T> list) {
        if (list.size() <= PAGE_SIZE) {
            return 1;
        }
        return 0;
    }

    private String convertToType(final String categoryCode) {
        if (categoryCode.compareTo("701") == 0) {
            return "notices";
        }
        if (categoryCode.compareTo(OTO_CODE) == 0) {
            return "oto-inquiries";
        }
        if (categoryCode.compareTo("703") == 0) {
            return "faqs";
        }
        throw new NotFoundException("카테고리 분류를 찾을 수 없습니다.");
    }

}
