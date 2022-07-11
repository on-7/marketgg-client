package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.domain.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.domain.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final static String DEFAULT_PRODUCT_URI = "/admin/v1/products";

    // @PostMapping
    // ModelAndView createProduct(@ModelAttribute final ProductCreateRequest productRequest) {
    //
    //     ModelAndView mav = new ModelAndView("redirect:" + DEFAULT_PRODUCT_URI + "/index");
    //     productService.createProduct(productRequest);
    //
    //     return mav;
    // }

    @GetMapping
    public ModelAndView retrieveProducts() {
        List<ProductResponse> products = productService.retrieveProducts();

        ModelAndView mav = new ModelAndView("product-view");
        mav.addObject("Products", products);

        return mav;
    }

    @GetMapping("/register")
    public ModelAndView registerProduct() {

        return new ModelAndView("product-form");
    }

    @PostMapping("/register")
    public ModelAndView doRegisterProduct(@RequestPart(value = "image") final MultipartFile image,
                                          @ModelAttribute final ProductCreateRequest productRequest) throws IOException {
        productService.createProduct(image, productRequest);

        return new ModelAndView("redirect:" + DEFAULT_PRODUCT_URI);
    }

}
