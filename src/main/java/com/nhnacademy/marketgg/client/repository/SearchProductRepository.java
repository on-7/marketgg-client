package com.nhnacademy.marketgg.client.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.elastic.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.elastic.response.SearchProductResponse;
import java.util.List;
import org.json.simple.parser.ParseException;

/**
 * 검색 Repository 입니다.
 *
 * @version 1.0.0
 */
public interface SearchProductRepository {

    /**
     * 지정한 카테고리에서 상품에 대한 검색을 할 수 있는 메소드입니다.
     *
     * @param code    - 지정한 카테고리의 식별번호입니다.
     * @param request - 검색을 진행 할 조건을 담은 객체입니다.
     * @return 지정한 카테고리 내의 검색어를 통한 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0
     */
    List<SearchProductResponse> searchProductForCategory(final String code,
                                                         final SearchRequest request)
            throws JsonProcessingException, ParseException;


    /**
     * 전체 목록에서 상품에 대한 검색을 할 수 있는 메소드입니다.
     *
     * @param request - 검색을 진행 할 조건을 담은 객체입니다.
     * @return 검색어를 통한 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0
     */
    List<SearchProductResponse> searchProductWithKeyword(final SearchRequest request)
            throws JsonProcessingException, ParseException;

    /**
     * 지정한 카테고리에서 상품에 대한 검색 후 가격별로 정렬할 수 있는 메소드입니다.
     *
     * @param code    - 지정한 카테고리의 식별번호입니다.
     * @param request - 검색을 진행 할 조건을 담은 객체입니다.
     * @param type    - 가격의 정렬 타입입니다. (asc, desc)
     * @return 지정한 카테고리 내의 검색어를 통한 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0
     */
    List<SearchProductResponse> searchProductForCategorySortPrice(final String code,
                                                                  final SearchRequest request,
                                                                  final String type)
            throws JsonProcessingException, ParseException;

    /**
     * 전체 목록에서 상품에 대한 검색 후 가격별로 정렬할 수 있는 메소드입니다.
     *
     * @param request - 검색을 진행 할 조건을 담은 객체입니다.
     * @param type    - 가격의 정렬 타입입니다. (asc, desc)
     * @return 검색어를 통한 가격 별 정렬 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0
     */
    List<SearchProductResponse> searchProductForKeywordSortPrice(final SearchRequest request,
                                                                 final String type)
            throws JsonProcessingException, ParseException;

}
