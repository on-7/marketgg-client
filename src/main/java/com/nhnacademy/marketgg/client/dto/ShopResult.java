package com.nhnacademy.marketgg.client.dto;

import com.nhnacademy.marketgg.client.dto.response.common.ErrorEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Market GG 에서 공통 응답 객체입니다.
 *
 * @param <T> - 응답 객체 타입
 * @author 김정민
 * @author 김훈민
 * @author 민아영
 * @author 박세완
 * @author 윤동열
 * @author 이제훈
 * @author 조현진
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public class ShopResult<T> {

    private final boolean success;

    private final T data;

    private final ErrorEntity error;

}