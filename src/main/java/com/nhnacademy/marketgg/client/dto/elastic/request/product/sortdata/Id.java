package com.nhnacademy.marketgg.client.dto.elastic.request.product.sortdata;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 해당 데이터의 식별번호로 정렬을 내림/오름 차순을 지정 할 수 있습니다.
 *
 * @version 1.0.0
 */
@AllArgsConstructor
@Getter
public class Id {

    /**
     * 내림/오름 차순을 지정 할 수 있습니다.
     *
     * @since 1.0.0
     */
    private String order;

}
