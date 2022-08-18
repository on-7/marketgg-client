package com.nhnacademy.marketgg.client.dto.response.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Market GG 에서 공통 응답 객체입니다.
 *
 * @param <T> - 응답 객체 타입
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public class CommonResult<T> {

    private final boolean success;

    private final T data;

    private final ErrorEntity error;

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(true, data, null);
    }

    public static <T> CommonResult<T> error(ErrorEntity error) {
        return new CommonResult<>(false, null, error);
    }

}
