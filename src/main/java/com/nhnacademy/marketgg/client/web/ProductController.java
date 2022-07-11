package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.domain.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.domain.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("products/index");
    }

    @PostMapping("/create")
    public ModelAndView createProduct(@RequestPart(value = "image") final MultipartFile image,
                                      @ModelAttribute final ProductCreateRequest productRequest) throws IOException {
        productService.createProduct(image, productRequest);

        return new ModelAndView("redirect:" + DEFAULT_PRODUCT_URI + "/index");
    }

    @GetMapping("/create")
    public ModelAndView createProduct() {

        return new ModelAndView("products/product-form");
    }

    @GetMapping
    public ModelAndView retrieveProducts() {

        List<ProductResponse> products = productService.retrieveProducts();

        ModelAndView mav = new ModelAndView("products/retrieve-products");
        mav.addObject("products", products);

        return mav;
    }

    @GetMapping("/{productNo}")
    public ModelAndView retrieveProductDetails(@PathVariable Long productNo) {

        ProductResponse productDetails = productService.retrieveProductDetails(productNo);

        ModelAndView mav = new ModelAndView("products/product-details");
        mav.addObject("productDetails", productDetails);

        return mav;
    }
}

