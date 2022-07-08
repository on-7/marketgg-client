package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.domain.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    ModelAndView createLabel(@ModelAttribute final ProductCreateRequest productRequest) {

        ModelAndView mav = new ModelAndView("redirect:/admin/v1/products/index");
        productService.createProduct(productRequest);

        return mav;
    }
}
