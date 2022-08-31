package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.dto.common.MemberInfo;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.product.ProductListResponse;
import com.nhnacademy.marketgg.client.paging.Pagination;
import com.nhnacademy.marketgg.client.service.product.ProductService;
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


    @GetMapping({ "/", "/index" })
    public ModelAndView index(MemberInfo memberInfo, @RequestParam(defaultValue = "0") int page) {
        log.info("MemberInfo = {}", memberInfo);

        ModelAndView mav = new ModelAndView("index");

        if (!memberInfo.isNull()) {
            mav.addObject("memberInfo", memberInfo);
        }

        PageResult<ProductListResponse> productList = this.productService.retrieveProducts(page);
        Pagination pagination = new Pagination(productList.getTotalPages(), page);
        List<ProductListResponse> products = productList.getData();

        mav.addObject("products", products);
        mav.addObject("keyword", null);
        mav.addObject("option", "main");
        mav.addObject("pages", pagination);
        mav.addObject("sort", null);

        return mav;
    }
}
