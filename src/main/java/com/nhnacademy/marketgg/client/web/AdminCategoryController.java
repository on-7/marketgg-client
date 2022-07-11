package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.client.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/v1/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/create")
    ModelAndView doCreateCategory() {
        ModelAndView mv = new ModelAndView();
        
        mv.setViewName("/categories/create-form");

        return mv;
    }

    @PostMapping
    ModelAndView createCategory(@ModelAttribute final CategoryCreateRequest categoryRequest)
            throws JsonProcessingException {

        ModelAndView mv = new ModelAndView();

        categoryService.createCategory(categoryRequest);

        mv.setViewName("redirect:/admin/v1/categories/index");

        return mv;
    }

    @GetMapping("/update")
    ModelAndView doUpdateCategory() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("/categories/update-form");

        return mv;
    }

    @PutMapping("/{categoryId}")
    ModelAndView updateCategory(@PathVariable final String categoryId,
                                @ModelAttribute final CategoryUpdateRequest categoryRequest)
            throws JsonProcessingException {

        ModelAndView mv = new ModelAndView();

        categoryService.updateCategory(categoryId, categoryRequest);
        
        mv.setViewName("redirect:/admin/v1/categories/index");

        return mv;
    }
    
    @DeleteMapping("/{categoryId}")
    ModelAndView deleteCategory(@PathVariable final String categoryId) {
        ModelAndView mv = new ModelAndView();

        categoryService.deleteCategory(categoryId);

        mv.setViewName("redirect:/admin/v1/categories/index");

        return mv;
    }

}
