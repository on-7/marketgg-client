package com.nhnacademy.marketgg.client.web.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.product.ProductInquiryResponse;
import com.nhnacademy.marketgg.client.dto.product.ProductListResponse;
import com.nhnacademy.marketgg.client.dto.product.ProductResponse;
import com.nhnacademy.marketgg.client.dto.review.ReviewRatingResponse;
import com.nhnacademy.marketgg.client.dto.review.ReviewResponse;
import com.nhnacademy.marketgg.client.dto.search.SearchRequestForCategory;
import com.nhnacademy.marketgg.client.paging.Pagination;
import com.nhnacademy.marketgg.client.service.product.ProductInquiryService;
import com.nhnacademy.marketgg.client.service.product.ProductService;
import com.nhnacademy.marketgg.client.service.review.ReviewService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController extends BaseController {

    private final ProductService productService;
    private final ReviewService reviewService;
    private final ProductInquiryService productInquiryService;
    private static final Integer PAGE_SIZE = 9;

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
    @ResponseBody
    public ModelAndView searchProductListByCategory(@RequestParam final String categoryId,
                                                    @RequestParam final String keyword,
                                                    @RequestParam final Integer page) throws JsonProcessingException {

        SearchRequestForCategory request = new SearchRequestForCategory(categoryId, keyword, page, PAGE_SIZE);
        PageResult<ProductListResponse> responses = productService.searchProductListByCategory(request);
        Pagination pagination = new Pagination(responses.getTotalPages(), page + 1);
        List<ProductListResponse> products = responses.getData();
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("pageType", "search");
        mav.addObject("keyword", keyword);
        mav.addObject("categoryId", categoryId);
        mav.addObject("products", products);
        mav.addObject("pages", pagination);
        mav.addObject("option", "search");
        mav.addObject("sort", null);

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
    public ModelAndView searchProductListByPrice(@PathVariable final String categoryId,
                                                 @PathVariable final String option, @RequestParam final String keyword,
                                                 @RequestParam final Integer page) throws JsonProcessingException {

        SearchRequestForCategory request = new SearchRequestForCategory(categoryId, keyword, page, PAGE_SIZE);
        PageResult<ProductListResponse> responses = productService.searchProductListByPrice(request, option);
        List<ProductListResponse> products = responses.getData();
        Pagination pagination = new Pagination(responses.getTotalPages(), page + 1);

        ModelAndView mav = new ModelAndView("index");
        mav.addObject("keyword", keyword);
        mav.addObject("categoryId", categoryId);
        mav.addObject("pageType", "priceSearch");
        mav.addObject("products", products);
        mav.addObject("pages", pagination);
        mav.addObject("option", "search");
        mav.addObject("sort", option);

        return mav;
    }

    /**
     * 모든 상품을 조회를 위한 GetMapping 을 지원 합니다.
     * 타임리프에서 products 로 조회할 수 있습니다.
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
        mav.addObject("keyword", null);
        mav.addObject("categoryId", null);
        mav.addObject("pageType", "default");
        mav.addObject("option", "main");
        mav.addObject("sort", null);

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
    public ModelAndView retrieveProductsByCategory(@PathVariable final String categoryCode,
                                                   @RequestParam(defaultValue = "1") int page) {

        PageResult<ProductListResponse> searchProductResponsePageResult =
                this.productService.retrieveProductsByCategory(
                        categoryCode, page);
        Pagination pagination = new Pagination(searchProductResponsePageResult.getTotalPages(), page);
        List<ProductListResponse> products = searchProductResponsePageResult.getData();


        ModelAndView mav = new ModelAndView("index");
        mav.addObject("products", products);
        mav.addObject("pages", pagination);
        mav.addObject("keyword", null);
        mav.addObject("categoryId", null);
        mav.addObject("pageType", "default");
        mav.addObject("option", "main");
        mav.addObject("sort", null);

        return mav;
    }


    /**
     * 상품 상세 조회를 위한 GetMapping 을 지원 합니다.
     * 타임리프에서 productDetails로 조회할 수 있습니다.
     *
     * @param productId - 상품의 PK 입니다.
     * @return - 상품 상세 페이지를 리턴합니다.
     * @since 1.0.0
     */
    @GetMapping("/{productId}")
    public ModelAndView retrieveProductDetails(@PathVariable final Long productId,
                                               @RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "1", value = "requestPage") int page2)
            throws JsonProcessingException {

        ProductResponse productDetails = this.productService.retrieveProductDetails(productId);
        ModelAndView mav = new ModelAndView(DEFAULT_PRODUCT_VIEW);
        mav.addObject("productDetails", productDetails);

        PageResult<ReviewResponse> reviewResponsePageResult = reviewService.retrieveReviews(productId, page);
        mav.addObject("reviews", reviewResponsePageResult.getData());

        CommonResult<List<ReviewRatingResponse>> listCommonResult = reviewService.retrieveReviewsByRating(productId);
        Long reviewCount = 0L;
        List<ReviewRatingResponse> data = listCommonResult.getData();

        for (ReviewRatingResponse datum : data) {
            reviewCount += datum.getRatingCount();
        }

        mav.addObject("reviewCount", reviewCount);
        mav.addObject("reviewRatings", data);

        Pagination pagination = new Pagination(reviewResponsePageResult.getTotalPages(), page);
        mav.addObject("pages", pagination);

        PageResult<ProductInquiryResponse> inquiries = productInquiryService.retrieveInquiryByProduct(page2, productId);
        Pagination inquiryPage = new Pagination(inquiries.getTotalPages(), page2);

        mav.addObject("productId", productId);
        mav.addObject("inquiries", inquiries.getData());
        mav.addObject("inquiryPage", inquiryPage);

        return mav;
    }

    /**
     * 검색어에 따른 추천 상품 목록을 반환합니다.
     *
     * @param keyword - 현재 검색어입니다.
     * @param page    - 검색할 페이지 정보입니다.
     * @return - 검색어에 따른 추천 상품 목록의 제목을 반환합니다.
     * @throws JsonProcessingException - JSON 과 관련한 예외처리를 담당합니다.
     * @since 1.0.0
     */
    @GetMapping("/suggest")
    @ResponseBody
    public String[] suggestionProductList(@RequestParam final String keyword,
                                          @RequestParam final Integer page) throws JsonProcessingException {

        SearchRequestForCategory request = new SearchRequestForCategory("001", keyword, page, 5);
        PageResult<ProductListResponse> responses = productService.searchProductListByCategory(request);
        List<ProductListResponse> products = responses.getData();
        String[] productNameList = new String[5];

        for (int i = 0; i < products.size(); i++) {
            productNameList[i] = products.get(i).getProductName();
            if (i == 4) {
                break;
            }
        }

        return productNameList;
    }

}
