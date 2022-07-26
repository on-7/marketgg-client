package com.nhnacademy.marketgg.client.dto.elastic.request.product;

import com.nhnacademy.marketgg.client.dto.elastic.request.searchutil.Query;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SearchProductForPrice {

    /**
     * 검색 결과 목록의 정렬 기준을 지정합니다.
     *
     * @since 1.0.0
     */
    private List<SortForPrice> sort;

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
     * 검색 조건을 지정할 수 있습니다. 검색어 및 검색을 진행 할 필드를 지정할 수 있습니다.
     *
     * @since 1.0.0
     */
    private Query query;

}
