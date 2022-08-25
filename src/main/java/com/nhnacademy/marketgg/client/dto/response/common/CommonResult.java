package com.nhnacademy.marketgg.client.dto.response.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Market GG 에서 공통 응답 객체입니다.
 *
 * @param <T> - 응답 객체 타입
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommonResult<T> {

    private boolean success;

    private T data;

    private ErrorEntity error;

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(true, data, null);
    }

    public static <T> CommonResult<T> error(ErrorEntity error) {
        return new CommonResult<>(false, null, error);
    }

}
