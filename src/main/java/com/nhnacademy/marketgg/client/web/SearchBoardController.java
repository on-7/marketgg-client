package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.elastic.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.elastic.response.SearchBoardResponse;
import com.nhnacademy.marketgg.client.service.SearchService;
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
 * 게시판 검색을 지원하는 Controller 입니다.
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/shop/v1/search/boards")
@RequiredArgsConstructor
public class SearchBoardController {

    private final SearchService searchService;

    private static final String SEARCH_RESULT = "search/board-search-list";

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
    @GetMapping("/categories/{categoryCode}")
    public ModelAndView searchForCategory(@PathVariable final String categoryCode,
                                          @RequestParam final String keyword, final Pageable pageable)
            throws ParseException, JsonProcessingException {

        ModelAndView mav = new ModelAndView(SEARCH_RESULT);
        SearchRequest request = new SearchRequest(keyword, PageRequest.of(pageable.getPageNumber(),
                                                                          pageable.getPageSize()));
        List<SearchBoardResponse> response =
                searchService.searchBoardForOption(categoryCode, request, "categoryCode");

        mav.addObject("response", response);
        mav.addObject("keyword", keyword);
        mav.addObject("page", pageable.getPageNumber());

        return mav;
    }

    /**
     * 선택한 사유 내에서 검색을 한 후, 검색 결과 목록을 담은 후 검색 목록 조회페이지로 이동합니다.
     * 
     * @param reason - 선택한 사유입니다.
     * @param keyword - 선택한 키워드입니다.
     * @param pageable - 검색 목록의 페이지 정보입니다.
     * @return 지정한 사유내의 검색어를 통한 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryCode}/reason")
    public ModelAndView searchForReason(@PathVariable final String categoryCode,
                                        @RequestParam final String reason,
                                        @RequestParam final String keyword, final Pageable pageable)
            throws ParseException, JsonProcessingException {

        ModelAndView mav = new ModelAndView(SEARCH_RESULT);
        SearchRequest request = new SearchRequest(keyword, PageRequest.of(pageable.getPageNumber(),
                                                                          pageable.getPageSize()));
        List<SearchBoardResponse> response =
                searchService.searchBoardForOption(reason, request, "reason");

        mav.addObject("response", response);
        mav.addObject("keyword", keyword);

        return mav;
    }

    /**
     * 선택한 상태 내에서 검색을 한 후, 검색 결과 목록을 담은 후 검색 목록 조회페이지로 이동합니다.
     * 
     * @param status - 선택한 상태입니다.
     * @param keyword - 선택한 키워드입니다.
     * @param pageable - 검색 목록의 페이지 정보입니다.
     * @return 지정한 사유내의 검색어를 통한 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0
     */
    @GetMapping("/categories/{categoryCode}/status")
    public ModelAndView searchForStatus(@PathVariable final String categoryCode,
                                        @RequestParam final String status,
                                        @RequestParam final String keyword, final Pageable pageable)
            throws ParseException, JsonProcessingException {

        ModelAndView mav = new ModelAndView(SEARCH_RESULT);
        SearchRequest request = new SearchRequest(keyword, PageRequest.of(pageable.getPageNumber(),
                                                                          pageable.getPageSize()));
        List<SearchBoardResponse> response =
                searchService.searchBoardForOption(status, request, "status");

        mav.addObject("response", response);
        mav.addObject("keyword", keyword);
        mav.addObject("page", pageable.getPageNumber());

        return mav;
    }

}
