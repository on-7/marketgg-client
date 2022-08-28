package com.nhnacademy.marketgg.client.web.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.PageResult;
import com.nhnacademy.marketgg.client.dto.request.SearchRequestForCategory;
import com.nhnacademy.marketgg.client.dto.response.ImageResponse;
import com.nhnacademy.marketgg.client.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.dto.response.ReviewResponse;
import com.nhnacademy.marketgg.client.dto.response.ProductListResponse;
import com.nhnacademy.marketgg.client.paging.Pagination;
import com.nhnacademy.marketgg.client.service.ImageService;
import com.nhnacademy.marketgg.client.service.ProductService;
import com.nhnacademy.marketgg.client.service.ReviewService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;
    private static final Integer PAGE_SIZE = 10;

    private final ImageService imageService;
    private static final String DEFAULT_PRODUCT_VIEW = "pages/products/product-view";

    /**
     * 지정한 카테고리 번호 내에서 검색한 상품 목록을 조회 한 후 이동하는 GET Mapping 을 지원합니다.
     *
     * @param categoryId - 지정한 카테고리의 식별번호입니다.
     * @param keyword    - 검색어입니다.
     * @param page       - 조회 할 페이지 정보입니다.
     * @return 선택한 카테고리 번호내에서 검색한 상품 목록을 구한 후 이동합니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    @GetMapping("/search")
    public ModelAndView searchProductListByCategory(@RequestParam final String categoryId, @RequestParam final String keyword, @RequestParam final Integer page) throws JsonProcessingException {

        SearchRequestForCategory request = new SearchRequestForCategory(categoryId, keyword, page, PAGE_SIZE);
        PageResult<List<ProductListResponse>> responses = productService.searchProductListByCategory(request);
        // FIXME: 검색 후 페이지로 채워주세요! (관리자 일시 관리자, 사용자 일시 사용자)
        // FIXME: Pathvariable 에 option 에는 (asc, desc) 만 들어갑니다! 매핑 잡으실 때 참고해주세요.
        ModelAndView mav = new ModelAndView("pages/products/index");
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
     * @param option     - 지정한 검색 옵션입니다.
     * @param keyword    - 검색어입니다.
     * @param page       - 조회 할 페이지 정보입니다.
     * @return 선택한 카테고리 번호내에서 선택한 정렬옵션으로 가격이 정렬된 상품 목록을 구한 후 이동합니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryId}/price/{option}/search")
    public ModelAndView searchProductListByPrice(@PathVariable final String categoryId, @PathVariable final String option, @RequestParam final String keyword, @RequestParam final Integer page) throws JsonProcessingException {

        SearchRequestForCategory request = new SearchRequestForCategory(categoryId, keyword, page, PAGE_SIZE);
        PageResult<List<ProductListResponse>> responses = productService.searchProductListByPrice(request, option);

        ModelAndView mav = new ModelAndView("pages/products/index");
        mav.addObject("keyword", keyword);
        mav.addObject("categoryId", categoryId);
        mav.addObject("responses", responses);
        mav.addObject("page", page);
        mav.addObject("option", option);

        return mav;
    }

    /**
     * 모든 상품을 조회를 위한 GetMapping 을 지원 합니다.
     * 타임리프에서 products로 조회할 수 있습니다.
     * List 타입 입니다.
     *
     * @return - 모든 상품 조회 페이지를 리턴 합니다.
     * @since 1.0.0
     */
    @GetMapping
    public ModelAndView retrieveProducts(@RequestParam(defaultValue = "0") int page) {
        PageResult<ProductListResponse> productResponsePageResult = this.productService.retrieveProducts(page);
        Pagination pagination = new Pagination(productResponsePageResult.getTotalPages(), page);
        List<ProductListResponse> products = productResponsePageResult.getData();

        ModelAndView mav = new ModelAndView("index");
        mav.addObject("products", products);

        mav.addObject("pages", pagination);

        return mav;
    }

    /**
     * 카테고리로 상품을 조회하기 위한 GetMapping을 지원 합니다.
     * 타임리프에서 products로 조회할 수 있습니다.
     *
     * @param categoryCode - 카테고리 소분류 입니다. ex) 101 - 채소
     * @return - 해당 카테고리를 가진 상품 목록 페이지를 리턴합니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryCode}")
    public ModelAndView retrieveProductsByCategory(@PathVariable final String categoryCode, @RequestParam(defaultValue = "0") int page) {

        PageResult<SearchProductResponse> searchProductResponsePageResult = this.productService.retrieveProductsByCategory(categoryCode, page);
        Pagination pagination = new Pagination(searchProductResponsePageResult.getTotalPages(), page);
        List<SearchProductResponse> products = searchProductResponsePageResult.getData();


        ModelAndView mav = new ModelAndView("index");
        mav.addObject("products", products);

        mav.addObject("pages", pagination);

        return mav;
    }


    /**
     * 상품 상세 조회를 위한 GetMapping 을 지원 합니다.
     * 타임리프에서 productDetails로 조회할 수 있습니다.
     *
     * @param id - 상품의 PK 입니다.
     * @return - 상품 상세 페이지를 리턴합니다.
     * @since 1.0.0
     */
    @GetMapping("/{id}")
    public ModelAndView retrieveProductDetails(@PathVariable final Long id, @RequestParam(defaultValue = "0") int page) {
        ProductResponse productDetails = this.productService.retrieveProductDetails(id);
        ModelAndView mav = new ModelAndView(DEFAULT_PRODUCT_VIEW);
        mav.addObject("productDetails", productDetails);

        PageResult<ReviewResponse> reviewResponsePageResult = reviewService.retrieveReviews(id, page);
        mav.addObject("reviews", reviewResponsePageResult.getData());

        Pagination pagination = new Pagination(reviewResponsePageResult.getTotalPages(), page);

        mav.addObject("pages", pagination);

        return mav;
    }

}
