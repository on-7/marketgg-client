package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductModifyRequest;
import com.nhnacademy.marketgg.client.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.service.ProductService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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

    // REVIEW 3
    @PostMapping("/create")
    public ModelAndView createProduct(@RequestPart(value = "image") final MultipartFile image,
                                      @ModelAttribute final ProductCreateRequest productRequest)
        throws IOException {

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

    @GetMapping("/{productId}")
    public ModelAndView retrieveProductDetails(@PathVariable Long productId) {

        ProductResponse productDetails = productService.retrieveProductDetails(productId);

        ModelAndView mav = new ModelAndView("products/product-details");
        mav.addObject("productDetails", productDetails);

        return mav;
    }

    @GetMapping("/{categorizationCode}/{categoryCode}")
    public ModelAndView retrieveProductsByCategory(@PathVariable String categorizationCode,
                                                   @PathVariable String categoryCode) {

        List<ProductResponse> products =
            productService.retrieveProductsByCategory(categorizationCode, categoryCode);

        ModelAndView mav = new ModelAndView("products/retrieve-products");
        mav.addObject("products", products);

        return mav;
    }

    @GetMapping("/update/{productId}")
    public ModelAndView updateProduct(@PathVariable Long productId) {

        ModelAndView mav = new ModelAndView("products/product-modify-form");

        ProductResponse product = productService.retrieveProductDetails(productId);
        mav.addObject("product", product);
        return mav;
    }

    // multipartFile의 경우 html form에서 PUT 맵핑을 적용시키기 어려워서 일단 POST로 구현.
    @PostMapping("/update/{productId}")
    public ModelAndView updateProduct(@RequestPart(value = "image") final MultipartFile image,
                                      @ModelAttribute final ProductModifyRequest productRequest,
                                      @PathVariable Long productId) throws IOException {

        productService.updateProduct(productId, image, productRequest);

        return new ModelAndView("redirect:" + DEFAULT_PRODUCT_URI + "/index");
    }

    @PostMapping("/{productId}/deleted")
    public ModelAndView deleteProduct(@PathVariable Long productId) {

        productService.deleteProduct(productId);

        return new ModelAndView("redirect:" + DEFAULT_PRODUCT_URI + "/index");
    }
}
