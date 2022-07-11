package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/v1/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/index")
    ModelAndView index() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("/categories/index");

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
