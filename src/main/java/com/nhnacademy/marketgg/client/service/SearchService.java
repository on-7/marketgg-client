package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.elastic.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.elastic.response.SearchBoardResponse;
import com.nhnacademy.marketgg.client.dto.elastic.response.SearchProductResponse;
import java.util.List;
import org.json.simple.parser.ParseException;

/**
 * 검색 Service 입니다.
 *
 * @version 1.0.0
 */
public interface SearchService {

    /**
     * 지정한 카테고리에서 상품에 대한 검색 후 반환하거나 지정한 타입으로 가격을 정렬후 반환 할수있는 메소드입니다.
     *
     * @param optionCode    - 지정한 카테고리의 식별번호입니다.
     * @param request       - 검색을 진행 할 조건을 담은 객체입니다.
     * @param priceSortType - 가격의 정렬 타입입니다. (asc, desc)
     * @return 지정한 카테고리 내의 검색어를 통한 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0
     */
    List<SearchProductResponse> searchProductForCategory(final String optionCode,
                                                         final SearchRequest request,
                                                         final String priceSortType)
            throws ParseException, JsonProcessingException;

    /**
     * 전체 목록에서 상품에 대한 검색 후 반환하거나 지정한 타입으로 가격을 정렬후 반환 할 수 있는 메소드입니다.
     *
     * @param request       - 검색을 진행 할 조건을 담은 객체입니다.
     * @param priceSortType - 가격의 정렬 타입입니다. (asc, desc)
     * @return 검색어를 통한 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0
     */
    List<SearchProductResponse> searchProductForKeyword(final SearchRequest request,
                                                        final String priceSortType)
            throws ParseException, JsonProcessingException;

    List<SearchBoardResponse> searchBoardForOption(final String optionCode,
                                                   final SearchRequest request,
                                                   final String option)
            throws ParseException, JsonProcessingException;

    List<SearchBoardResponse> searchBoardForKeyword(final SearchRequest request)
            throws ParseException, JsonProcessingException;


}
