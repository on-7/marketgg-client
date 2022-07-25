package com.nhnacademy.marketgg.client.dto.request.searchutil;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 정렬을 할 기준을 지정할 수 있습니다.
 *
 * @version 1.0.0
 */
@AllArgsConstructor
@Getter
public class Sort {

    private Score _score;

    private Id id;

}
