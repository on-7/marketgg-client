package com.nhnacademy.marketgg.client.web;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/v1/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/create")
    public ModelAndView doCreateCategory() {
        ModelAndView mav = new ModelAndView("/categories/create-form");

        List<CategorizationRetrieveResponse> responses = categoryService.retrieveCategorizations();

        mav.addObject("categorizationNames", responses);
        return mav;
    }

    @PostMapping
    public ModelAndView createCategory(@ModelAttribute final CategoryCreateRequest categoryRequest)
            throws JsonProcessingException {

        ModelAndView mav = new ModelAndView("redirect:/admin/v1/categories/index");

        categoryService.createCategory(categoryRequest);

        return mav;
    }

    @GetMapping("/index")
    public ModelAndView retrieveCategories() {
        ModelAndView mav = new ModelAndView("/categories/index");

        List<CategoryRetrieveResponse> responses = categoryService.retrieveCategories();

        mav.addObject("categories", responses);

        return mav;
    }

    @GetMapping("/update/{categoryId}")
    public ModelAndView doUpdateCategory(@PathVariable final String categoryId) {
        ModelAndView mav = new ModelAndView("/categories/update-form");

        CategoryRetrieveResponse categoryResponse = categoryService.retrieveCategory(categoryId);
        mav.addObject("category", categoryResponse);

        return mav;
    }

    @PutMapping("/{categoryId}")
    public ModelAndView updateCategory(@PathVariable final String categoryId,
                                @ModelAttribute final CategoryUpdateRequest categoryRequest)
            throws JsonProcessingException {

        ModelAndView mav = new ModelAndView("redirect:/admin/v1/categories/index");

        categoryService.updateCategory(categoryId, categoryRequest);

        return mav;
    }

    @DeleteMapping("/{categoryId}")
    public ModelAndView deleteCategory(@PathVariable final String categoryId) {
        ModelAndView mav = new ModelAndView("redirect:/admin/v1/categories/index");

        categoryService.deleteCategory(categoryId);

        return mav;
    }

}
