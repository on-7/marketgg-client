package com.nhnacademy.marketgg.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 반환 할 상품 리스트 조회 시 보여줄 필드를 모아둔 객체입니다.
 *
 * @version 1.0.0
 */
@NoArgsConstructor
@Getter
public class ProductListResponse {

    private Long id;

    private String categoryCode;

    private String productName;

    private String content;

    private String description;

    private String labelName;

    private String imageAddress;

    private Long price;

    private Long amount;

}
