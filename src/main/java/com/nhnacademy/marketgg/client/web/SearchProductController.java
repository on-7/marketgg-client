package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.elastic.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.elastic.response.SearchProductResponse;
import com.nhnacademy.marketgg.client.service.SearchProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 상품 검색을 지원하는 Controller 입니다.
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/shop/v1/search")
@RequiredArgsConstructor
public class SearchProductController {

    private final SearchProductService searchProductService;

    private static final String SEARCH_RESULT = "search/search-list";

    /**
     * 카테고리 내에서 검색어를 통한 검색 후, 검색 결과 목록을 담은 후 검색 목록 조회페이지로 이동합니다.
     *
     * @param categoryCode - 지정한 카테고리의 식별번호입니다.
     * @param keyword      - 검색을 진행할 검색어입니다.
     * @param pageable     - 검색 목록의 페이지 정보입니다.
     * @return 지정한 카테고리 내의 검색어를 통한 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryCode}/products")
    public ModelAndView searchForCategory(@PathVariable final String categoryCode,
                                          @RequestParam final String keyword,
                                          final Pageable pageable)
            throws ParseException, JsonProcessingException {

        ModelAndView mav = new ModelAndView(SEARCH_RESULT);
        SearchRequest request = new SearchRequest(keyword, PageRequest.of(pageable.getPageNumber(),
                                                                          pageable.getPageSize()));
        List<SearchProductResponse> response =
                searchProductService.searchForCategory(categoryCode, request);

        mav.addObject("response", response);
        mav.addObject("keyword", keyword);
        mav.addObject("page", pageable.getPageNumber());

        return mav;
    }

    /**
     * 검색어를 통한 검색 후, 검색 결과 목록을 담은 후 검색 목록 조회페이지로 이동합니다.
     *
     * @param keyword  - 검색을 진행할 검색어입니다.
     * @param pageable - 검색 목록의 페이지 정보입니다.
     * @return 지정한 카테고리 내의 검색어를 통한 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0essingException
     * @since 1.0.0
     */
    @GetMapping("/products")
    public ModelAndView searchForKeyword(@RequestParam final String keyword,
                                         final Pageable pageable)
            throws ParseException, JsonProcessingException {

        ModelAndView mav = new ModelAndView(SEARCH_RESULT);
        SearchRequest request = new SearchRequest(keyword, PageRequest.of(pageable.getPageNumber(),
                                                                          pageable.getPageSize()));
        List<SearchProductResponse> response = searchProductService.searchForKeyword(request);

        mav.addObject("response", response);
        mav.addObject("keyword", keyword);
        mav.addObject("page", pageable.getPageNumber());

        return mav;
    }

    /**
     * 카테고리 내에서 검색어를 통한 검색 후, 가격이 정렬된 검색 결과 목록을 담은 후 검색 목록 조회페이지로 이동합니다.
     *
     * @param categoryCode - 지정한 카테고리의 식별번호입니다.
     * @param keyword      - 검색을 진행할 검색어입니다.
     * @param pageable     - 검색 목록의 페이지 정보입니다.
     * @param type         - 가격의 정렬 타입입니다. (asc, desc)
     * @return 지정한 카테고리 내의 검색어를 통한 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryCode}/products/price/{type}")
    public ModelAndView searchForCategorySortPrice(@PathVariable final String categoryCode,
                                                   @PathVariable final String type,
                                                   @RequestParam final String keyword,
                                                   final Pageable pageable)
            throws ParseException, JsonProcessingException {

        ModelAndView mav = new ModelAndView(SEARCH_RESULT);
        SearchRequest request = new SearchRequest(keyword, PageRequest.of(pageable.getPageNumber(),
                                                                          pageable.getPageSize()));
        List<SearchProductResponse> response =
                searchProductService.searchForCategorySortPrice(categoryCode, request, type);

        mav.addObject("response", response);
        mav.addObject("keyword", keyword);

        return mav;
    }

    /**
     * 검색어를 통한 검색 후, 가격이 정렬된 검색 결과 목록을 담은 후 검색 목록 조회페이지로 이동합니다.
     *
     * @param keyword  - 검색을 진행할 검색어입니다.
     * @param pageable - 검색 목록의 페이지 정보입니다.
     * @param type     - 가격의 정렬 타입입니다. (asc, desc)
     * @return 지정한 카테고리 내의 검색어를 통한 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0essingException
     * @since 1.0.0
     */
    @GetMapping("/products/price/{type}")
    public ModelAndView searchForKeywordSortPrice(@RequestParam final String keyword,
                                                  @PathVariable final String type,
                                                  final Pageable pageable)
            throws ParseException, JsonProcessingException {

        ModelAndView mav = new ModelAndView(SEARCH_RESULT);
        SearchRequest request = new SearchRequest(keyword, PageRequest.of(pageable.getPageNumber(),
                                                                          pageable.getPageSize()));
        List<SearchProductResponse> response =
                searchProductService.searchForKeywordSortPrice(request, type);

        mav.addObject("response", response);
        mav.addObject("keyword", keyword);
        mav.addObject("page", pageable.getPageNumber());

        return mav;
    }

}
