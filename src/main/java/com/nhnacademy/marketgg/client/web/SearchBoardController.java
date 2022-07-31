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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @PostMapping("/categories/{categoryCode}/types/{type}")
    public ModelAndView searchForCategory(@PathVariable final String categoryCode, @PathVariable final String type,
                                          @RequestParam final String keyword,
                                          final Pageable pageable)
            throws ParseException, JsonProcessingException {

        ModelAndView mav = new ModelAndView("board/" + type + "/index");
        SearchRequest request = new SearchRequest(keyword, PageRequest.of(pageable.getPageNumber(),
                                                                          pageable.getPageSize()));
        List<SearchBoardResponse> response =
                searchService.searchBoardForCategory(categoryCode, request, "categoryCode");

        mav.addObject("responses", response);
        mav.addObject("keyword", keyword);
        mav.addObject("page", pageable.getPageNumber());
        mav.addObject("searchType", "default");
        mav.addObject("isEnd", this.checkPageEnd(response));

        return mav;
    }

    /**
     * 선택한 카테고리의 선택한 사유 내에서 검색을 한 후, 검색 결과 목록을 담은 후 검색 목록 조회페이지로 이동합니다.
     *
     * @param reason   - 선택한 사유입니다.
     * @param keyword  - 선택한 키워드입니다.
     * @param pageable - 검색 목록의 페이지 정보입니다.
     * @return 지정한 사유내의 검색어를 통한 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0
     */
    @PostMapping("/categories/{categoryCode}/reason")
    public ModelAndView searchForReason(@PathVariable final String categoryCode,
                                        @RequestParam final String reason,
                                        @RequestParam final String keyword, final Pageable pageable)
            throws ParseException, JsonProcessingException {

        ModelAndView mav = new ModelAndView("board/oto-inquiries/index");
        SearchRequest request = new SearchRequest(keyword, PageRequest.of(pageable.getPageNumber(),
                                                                          pageable.getPageSize()));
        List<SearchBoardResponse> response =
                searchService.searchBoardForOption(categoryCode, reason, request, "reason");

        mav.addObject("responses", response);
        mav.addObject("keyword", keyword);
        mav.addObject("page", pageable.getPageNumber());
        mav.addObject("searchType", "reason");
        mav.addObject("isEnd", this.checkPageEnd(response));

        return mav;
    }

    /**
     * 선택한 카테고리의 선택한 상태 내에서 검색을 한 후, 검색 결과 목록을 담은 후 검색 목록 조회페이지로 이동합니다.
     *
     * @param status   - 선택한 상태입니다.
     * @param keyword  - 선택한 키워드입니다.
     * @param pageable - 검색 목록의 페이지 정보입니다.
     * @return 지정한 사유내의 검색어를 통한 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0
     */
    @PostMapping("/categories/{categoryCode}/status")
    public ModelAndView searchForStatus(@PathVariable final String categoryCode,
                                        @RequestParam final String status,
                                        @RequestParam final String keyword, final Pageable pageable)
            throws ParseException, JsonProcessingException {

        ModelAndView mav = new ModelAndView("board/oto-inquiries/index");
        SearchRequest request = new SearchRequest(keyword, PageRequest.of(pageable.getPageNumber(),
                                                                          pageable.getPageSize()));
        List<SearchBoardResponse> response =
                searchService.searchBoardForOption(categoryCode, status, request, "status");

        mav.addObject("responses", response);
        mav.addObject("keyword", keyword);
        mav.addObject("page", pageable.getPageNumber());
        mav.addObject("searchType", "status");
        mav.addObject("isEnd", this.checkPageEnd(response));

        return mav;
    }

    private <T> Integer checkPageEnd(List<T> list) {
        if (list.size() < 11) {
            return 1;
        }
        return 0;
    }

}
