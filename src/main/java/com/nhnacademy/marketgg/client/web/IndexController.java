package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.PageResult;
import com.nhnacademy.marketgg.client.dto.response.ProductListResponse;
import com.nhnacademy.marketgg.client.paging.Pagination;
import com.nhnacademy.marketgg.client.service.ImageService;
import com.nhnacademy.marketgg.client.service.ProductService;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final ProductService productService;
    private final ImageService imageService;
    private static final String DEFAULT_PRODUCT_VIEW = "pages/products/product-view";


    @GetMapping({"/", "/index"})
    public ModelAndView index(MemberInfo memberInfo, @RequestParam(defaultValue = "0") int page) {
        log.info("MemberInfo = {}", memberInfo);

        ModelAndView mav = new ModelAndView("index");

        if (!memberInfo.isNull()) {
            mav.addObject("memberInfo", memberInfo);
        }

        PageResult<ProductListResponse> searchProductResponsePageResult = this.productService.retrieveProducts(page);
        Pagination pagination = new Pagination(searchProductResponsePageResult.getTotalPages(), page);
        List<ProductListResponse> products = searchProductResponsePageResult.getData();

        mav.addObject("products", products);
        mav.addObject("pages", pagination);

        return mav;
    }
}
