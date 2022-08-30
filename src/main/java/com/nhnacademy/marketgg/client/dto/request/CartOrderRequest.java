package com.nhnacademy.marketgg.client.dto.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 장바구니에 담은 상품 아이디 목록이 담긴 요청 객체입니다.
 *
 * @author 김정민
 * @author 윤동열
 * @author 이제훈
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class CartOrderRequest {

    @NotNull
    private final List<Long> id;

}
