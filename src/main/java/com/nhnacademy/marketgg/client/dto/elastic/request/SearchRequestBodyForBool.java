package com.nhnacademy.marketgg.client.dto.elastic.request;

import com.nhnacademy.marketgg.client.dto.elastic.request.searchutil.Bool;
import com.nhnacademy.marketgg.client.dto.elastic.request.searchutil.BoolQuery;
import com.nhnacademy.marketgg.client.dto.elastic.request.searchutil.MultiMatch;
import com.nhnacademy.marketgg.client.dto.elastic.request.searchutil.Must;
import java.util.Collections;
import java.util.List;
import lombok.Getter;

/**
 * 카테고리 내에서 검색 시, 해당 객체를 통해 요청을 진행합니다.
 *
 * @version 1.0.0
 */
@Getter
public class SearchRequestBodyForBool<T> {

    private static final List<String> CATEGORY_FIELD = List.of("categoryCode");
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
     * 검색 기준을 지정합니다. 검색어 및 검색을 진행 할 필드를 지정할 수 있습니다.
     *
     * @since 1.0.0
     */
    private final BoolQuery query;

    public SearchRequestBodyForBool(final String optionCode, final T sortMap,
                                    final SearchRequest request, final String option) {

        List<String> requestOption = DEFAULT_PRODUCT_FIELD;

        this.sort = Collections.singletonList(sortMap);
        this.from = request.getPageRequest().getPageNumber();
        this.size = request.getPageRequest().getPageSize();
        if (option.compareTo("board") == 0) {
            requestOption = DEFAULT_BOARD_FIELD;
        }
        this.query = new BoolQuery(
                        new Bool(new Must(List.of(new MultiMatch(optionCode, CATEGORY_FIELD),
                                                  new MultiMatch(request.getRequest(),
                                                                 requestOption)))));
    }

    public SearchRequestBodyForBool(final String categoryCode, final T sortMap,
                                    final SearchRequest request, final String optionCode,
                                    final String option) {
        this.sort = Collections.singletonList(sortMap);
        this.from = request.getPageRequest().getPageNumber();
        this.size = request.getPageRequest().getPageSize();
        this.query = new BoolQuery(new Bool(new Must(List.of(new MultiMatch(categoryCode, CATEGORY_FIELD),
                                          new MultiMatch(optionCode, List.of(option)),
                                          new MultiMatch(request.getRequest(),
                                                         DEFAULT_PRODUCT_FIELD)))));
    }

}
