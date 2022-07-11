package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/v1/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/index")
    ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        List<CategoryRetrieveResponse> responses = categoryService.retrieveCategories();

        mv.setViewName("/categories/index");
        mv.addObject("categories", responses);

        return mv;
    }

}
