package com.nhnacademy.marketgg.client.web.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * 카테고리에 관련된 Controller 입니다.
 *
 * @author 박세완, 김정민
 * @version 1.0.0
 */
@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    private static final String DEFAULT_CATEGORY = "pages/admin/categories";

    /**
     * 카테고리 분류표 목록을 담은 후, 카테고리 등록 FORM 으로 이동하는 메소드입니다.
     *
     * @return 카테고리 분류표 목록을 가지고 카테고리 등록 FORM 으로 이동합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @GetMapping("/create")
    public ModelAndView doCreateCategory() throws UnAuthenticException, UnAuthorizationException {
        ModelAndView mav = new ModelAndView(DEFAULT_CATEGORY + "/create-form");

        List<CategorizationRetrieveResponse> responses = categoryService.retrieveCategorizations();

        mav.addObject("categorizations", responses);
        return mav;
    }

    /**
     * 입력받은 정보로 카테고리를 등록하는 메소드를 실행하고 다시 카테고리 Index 페이지로 이동하는 메소드입니다.
     *
     * @param categoryRequest - 카테고리 등록을 위한 정보를 담은 객체입니다.
     * @return 카테고리의 Index 페이지로 REDIRECT 합니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @PostMapping
    public ModelAndView createCategory(@ModelAttribute final CategoryCreateRequest categoryRequest)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        ModelAndView mav = new ModelAndView("redirect:/" + DEFAULT_CATEGORY + "/index");

        categoryService.createCategory(categoryRequest);

        return mav;
    }

    /**
     * 카테고리의 전체 목록을 조회한 정보를 담고, 다시 카테고리 Index 페이지로 이동하는 메소드입니다.
     *
     * @return 카테고리의 전체 목록 List 를 가지고 카테고리 Index 페이지로 이동합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @GetMapping("/index")
    public ModelAndView retrieveCategories() throws UnAuthenticException, UnAuthorizationException {
        ModelAndView mav = new ModelAndView(DEFAULT_CATEGORY + "/index");

        List<CategoryRetrieveResponse> responses = categoryService.retrieveCategories();

        mav.addObject("categories", responses);

        return mav;
    }

    /**
     * 수정할 카테고리를 담은 후 카테고리를 수정할 정보를 입력하는 FORM 으로 이동하는 메소드입니다.
     *
     * @param categoryId - 수정할 카테고리의 식별번호입니다.
     * @return 수정할 카테고리를 가지고 카테고리 수정 FORM 으로 이동합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @GetMapping("/update/{categoryId}")
    public ModelAndView doUpdateCategory(@PathVariable final String categoryId)
        throws UnAuthenticException, UnAuthorizationException {
        ModelAndView mav = new ModelAndView(DEFAULT_CATEGORY + "/update-form");

        CategoryRetrieveResponse categoryResponse = categoryService.retrieveCategory(categoryId);
        mav.addObject("category", categoryResponse);

        return mav;
    }

    /**
     * 입력받은 정보로 카테고리를 수정하는 메소드를 실행하고 다시 카테고리의 Index 페이지로 이동하는 메소드입니다.
     *
     * @param categoryId      - 수정할 카테고리의 식별번호입니다.
     * @param categoryRequest - 카테고리를 수정할 입력정보를 담은 객체입니다.
     * @return 카테고리의 Index 페이지로 REDIRECT 합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @PutMapping("/{categoryId}")
    public ModelAndView updateCategory(@PathVariable final String categoryId,
                                       @ModelAttribute final CategoryUpdateRequest categoryRequest)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        ModelAndView mav = new ModelAndView("redirect:/" + DEFAULT_CATEGORY + "/index");

        categoryService.updateCategory(categoryId, categoryRequest);

        return mav;
    }

    /**
     * 지정한 카테고리를 삭제 한 후, 다시 카테고리의 Index 페이지로 이동하는 메소드입니다.
     *
     * @param categoryId - 삭제할 카테고리의 식별번호입니다.
     * @return 카테고리의 Index 페이지로 REDIRECT 합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @DeleteMapping("/{categoryId}")
    public ModelAndView deleteCategory(@PathVariable final String categoryId)
        throws UnAuthenticException, UnAuthorizationException {
        ModelAndView mav = new ModelAndView("redirect:/" + DEFAULT_CATEGORY + "/index");

        categoryService.deleteCategory(categoryId);

        return mav;
    }

}
