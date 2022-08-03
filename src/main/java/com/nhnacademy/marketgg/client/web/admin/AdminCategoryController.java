package com.nhnacademy.marketgg.client.web.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.service.CategoryService;
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

import java.util.List;


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

    /**
     * 카테고리 분류표 목록을 담은 후, 카테고리 등록 FORM 으로 이동하는 메소드입니다.
     *
     * @return 카테고리 분류표 목록을 가지고 카테고리 등록 FORM 으로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/create")
    public ModelAndView doCreateCategory() {
        ModelAndView mav = new ModelAndView("/categories/create-form");

        List<CategorizationRetrieveResponse> responses = categoryService.retrieveCategorizations();

        mav.addObject("categorizations", responses);
        return mav;
    }

    /**
     * 입력받은 정보로 카테고리를 등록하는 메소드를 실행하고 다시 카테고리 Index 페이지로 이동하는 메소드입니다.
     *
     * @param categoryRequest - 카테고리 등록을 위한 정보를 담은 객체입니다.
     * @return 카테고리의 Index 페이지로 REDIRECT 합니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    @PostMapping
    public ModelAndView createCategory(@ModelAttribute final CategoryCreateRequest categoryRequest)
            throws JsonProcessingException {

        ModelAndView mav = new ModelAndView("redirect:/shop/v1/admin/categories/index");

        categoryService.createCategory(categoryRequest);

        return mav;
    }

    /**
     * 카테고리의 전체 목록을 조회한 정보를 담고, 다시 카테고리 Index 페이지로 이동하는 메소드입니다.
     *
     * @return 카테고리의 전체 목록 List 를 가지고 카테고리 Index 페이지로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/index")
    public ModelAndView retrieveCategories() {
        ModelAndView mav = new ModelAndView("/categories/index");

        List<CategoryRetrieveResponse> responses = categoryService.retrieveCategories();

        mav.addObject("categories", responses);

        return mav;
    }

    /**
     * 수정할 카테고리를 담은 후 카테고리를 수정할 정보를 입력하는 FORM 으로 이동하는 메소드입니다.
     *
     * @param categoryId - 수정할 카테고리의 식별번호입니다.
     * @return 수정할 카테고리를 가지고 카테고리 수정 FORM 으로 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/update/{categoryId}")
    public ModelAndView doUpdateCategory(@PathVariable final String categoryId) {
        ModelAndView mav = new ModelAndView("/categories/update-form");

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
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    @PutMapping("/{categoryId}")
    public ModelAndView updateCategory(@PathVariable final String categoryId,
                                       @ModelAttribute final CategoryUpdateRequest categoryRequest)
            throws JsonProcessingException {

        ModelAndView mav = new ModelAndView("redirect:/shop/v1/admin/categories/index");

        categoryService.updateCategory(categoryId, categoryRequest);

        return mav;
    }

    /**
     * 지정한 카테고리를 삭제 한 후, 다시 카테고리의 Index 페이지로 이동하는 메소드입니다.
     *
     * @param categoryId - 삭제할 카테고리의 식별번호입니다.
     * @return 카테고리의 Index 페이지로 REDIRECT 합니다.
     * @since 1.0.0
     */
    @DeleteMapping("/{categoryId}")
    public ModelAndView deleteCategory(@PathVariable final String categoryId) {
        ModelAndView mav = new ModelAndView("redirect:/shop/v1/admin/categories/index");

        categoryService.deleteCategory(categoryId);

        return mav;
    }

}
