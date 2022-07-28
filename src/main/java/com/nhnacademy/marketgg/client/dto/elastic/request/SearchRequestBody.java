package com.nhnacademy.marketgg.client.dto.elastic.request;

import com.nhnacademy.marketgg.client.dto.elastic.request.searchutil.MultiMatch;
import com.nhnacademy.marketgg.client.dto.elastic.request.searchutil.Query;
import java.util.Collections;
import java.util.List;
import lombok.Getter;

/**
 * 전체 목록에서 검색 할 시 해당 객체를 통해 요청을 보냅니다.
 *
 * @version 1.0.0
 */
@Getter
public class SearchRequestBody<T> {

    private static final List<String> DEFAULT_PRODUCT_FIELD =
            List.of("productName", "productName.forSyno", "content", "content.forSyno",
                    "description", "description.forSyno");

    private static final List<String> DEFAULT_BOARD_FIELD =
            List.of("title", "title.forSyno");

    /**
     * 검색 결과 목록의 정렬 기준을 지정합니다.
     *
     * @since 1.0.0
     */
    private final List<T> sort;

    /**
     * 검색 결과 목록의 페이지 번호를 지정합니다.
     *
     * @since 1.0.0
     */
    private final Integer from;

    /**
     * 검색 결과 목록의 페이지 크기를 지정합니다.
     *
     * @since 1.0.0
     */
    private final Integer size;

    /**
     * 검색 조건을 지정할 수 있습니다. 검색어 및 검색을 진행 할 필드를 지정할 수 있습니다.
     *
     * @since 1.0.0
     */
    private final Query query;

    public SearchRequestBody(T sortMap, SearchRequest request, String document) {
        this.sort = Collections.singletonList(sortMap);
        this.from = request.getPageRequest().getPageNumber();
        this.size = request.getPageRequest().getPageSize();
        if (document.compareTo("product") == 0) {
            this.query = new Query(new MultiMatch(request.getRequest(), DEFAULT_PRODUCT_FIELD));
        } else {
            this.query = new Query(new MultiMatch(request.getRequest(), DEFAULT_BOARD_FIELD));
        }
    }

}
