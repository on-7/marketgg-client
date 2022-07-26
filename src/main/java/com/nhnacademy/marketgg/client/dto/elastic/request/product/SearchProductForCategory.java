package com.nhnacademy.marketgg.client.dto.elastic.request.product;

import com.nhnacademy.marketgg.client.dto.elastic.request.searchutil.BoolQuery;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 카테고리 내에서 검색 시, 해당 객체를 통해 요청을 진행합니다.
 *
 * @version 1.0.0
 */
@Builder
@AllArgsConstructor
@Getter
public class SearchProductForCategory {

    /**
     * 검색 결과 목록의 정렬 기준을 지정합니다.
     *
     * @since 1.0.0
     */
    private List<Sort> sort;

    /**
     * 검색 결과 목록의 페이지 번호를 지정합니다.
     *
     * @since 1.0.0
     */
    private Integer from;

    /**
     * 검색 결과 목록의 페이지 크기를 지정합니다.
     *
     * @since 1.0.0
     */
    private Integer size;

    /**
     * 검색 기준을 지정합니다. 검색어 및 검색을 진행 할 필드를 지정할 수 있습니다.
     *
     * @since 1.0.0
     */
    private BoolQuery query;

}
