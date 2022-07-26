package com.nhnacademy.marketgg.client.dto.elastic.request.searchutil;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 검색 조건을 선언 할 수 있습니다.
 *
 * @version 1.0.0
 */
@AllArgsConstructor
@Getter
public class Bool {

    private Must must;

}
