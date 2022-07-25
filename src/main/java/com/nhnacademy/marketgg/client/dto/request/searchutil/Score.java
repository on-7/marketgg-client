package com.nhnacademy.marketgg.client.dto.request.searchutil;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 검색어와 일치하는 정도에 따라 내림/오름 차순으로 정렬할 수 있습니다.
 *
 * @version 1.0.0
 */
@AllArgsConstructor
@Getter
public class Score {

    /**
     * 내림/오름 차순을 지정 할 수 있습니다.
     *
     * @since 1.0.0
     */
    private String order;

}
