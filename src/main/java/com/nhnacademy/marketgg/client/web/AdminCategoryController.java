package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/v1/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;


}
