package com.nhnacademy.marketgg.client.dto.request.searchutil;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 일반적인 쿼리 질의어를 지정할 수 있습니다..
 *
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public class Query {

    private MultiMatch multi_match;

}
