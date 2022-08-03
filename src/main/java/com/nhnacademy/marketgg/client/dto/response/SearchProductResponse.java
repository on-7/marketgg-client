package com.nhnacademy.marketgg.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ES Server 에서 반환 할 상품 리스트 조회 시 보여줄 필드를 모아둔 객체입니다.
 *
 * @version 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchProductResponse {

    private Long id;

    private String productName;

    private String categoryCode;

    private String labelName;

    private String imageAddress;

    private Long price;

    private Long amount;

    private String content;

}
