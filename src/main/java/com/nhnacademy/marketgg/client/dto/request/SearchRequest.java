package com.nhnacademy.marketgg.client.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

/**
 * 사용자의 검색 및 반환 할 페이지를 표시합니다.
 *
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class SearchRequest {

    /**
     * 사용자의 검색 키워드입니다.
     *
     * @since 1.0.0
     */
    private final String request;

    /**
     * 반환 할 페이지 정보입니다.
     *
     * @since 1.0.0
     */
    private final PageRequest pageRequest;

}
