package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.dto.response.SearchProductResponse;
import com.nhnacademy.marketgg.client.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 전체 목록에서 검색한 상품 목록을 조회 후 이동하는 GET Mapping 을 지원합니다..
     *
     * @param keyword - 검색어입니다.
     * @param page - 조회 할 페이지 정보입니다.
     * @return 전체 목록에서 검색한 상품 목록을 구한 후 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/search")
    public ModelAndView searchProductList(@RequestParam final String keyword,
                                          @RequestParam final String page) {

        List<SearchProductResponse> responses = productService.searchProductList(keyword, page);

        // FIXME: 검색 후 페이지로 채워주세요! (관리자 일시 관리자, 사용자 일시 사용자)
        ModelAndView mav = new ModelAndView();
        mav.addObject("keyword", keyword);
        mav.addObject("responses", responses);
        mav.addObject("page", page);

        return mav;
    }

    /**
     * 지정한 카테고리 번호 내에서 검색한 상품 목록을 조회 한 후 이동하는 GET Mapping 을 지원합니다.
     *
     * @param categoryId - 지정한 카테고리의 식별번호입니다.
     * @param keyword - 검색어입니다.
     * @param page - 조회 할 페이지 정보입니다.
     * @return 선택한 카테고리 번호내에서 검색한 상품 목록을 구한 후 이동합니다..
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryId}/search")
    public ModelAndView searchProductListByCategory(@PathVariable final String categoryId,
                                                    @RequestParam final String keyword,
                                                    @RequestParam final String page) {

        List<SearchProductResponse> responses = productService.searchProductListByCategory(categoryId, keyword, page);

        // FIXME: 검색 후 페이지로 채워주세요! (관리자 일시 관리자, 사용자 일시 사용자)
        ModelAndView mav = new ModelAndView();
        mav.addObject("keyword", keyword);
        mav.addObject("categoryId", categoryId);
        mav.addObject("responses", responses);
        mav.addObject("page", page);

        return mav;
    }

    /**
     * 지정한 카테고리 번호 내에서 선택한 옵션으로 가격이 정렬된 상품 목록을 조회 한 후 이동하는 GET Mapping 을 지원합니다.
     *
     * @param categoryId - 지정한 카테고리의 식별번호입니다.
     * @param option - 지정한 검색 옵션입니다.
     * @param keyword - 검색어입니다.
     * @param page - 조회 할 페이지 정보입니다.
     * @return 선택한 카테고리 번호내에서 선택한 정렬옵션으로 가격이 정렬된 상품 목록을 구한 후 이동합니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryId}/price/{option}/search")
    public ModelAndView searchProductListByPrice(@PathVariable final String categoryId,
                                                 @PathVariable final String option,
                                                 @RequestParam final String keyword,
                                                 @RequestParam final String page) {

        List<SearchProductResponse> responses = productService.searchProductListByPrice(categoryId, option, keyword, page);

        // FIXME: 검색 후 페이지로 채워주세요! (관리자 일시 관리자, 사용자 일시 사용자
        ModelAndView mav = new ModelAndView();
        mav.addObject("keyword", keyword);
        mav.addObject("categoryId", categoryId);
        mav.addObject("responses", responses);
        mav.addObject("page", page);
        mav.addObject("option", option);

        return mav;
    }

}
