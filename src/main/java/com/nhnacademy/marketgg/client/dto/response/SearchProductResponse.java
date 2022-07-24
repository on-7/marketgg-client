package com.nhnacademy.marketgg.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SearchProductResponse {

    private Long id;

    private String productName;

    private String categoryCode;

    private String labelName;

    private String imageAddress;

    private Long price;

    private String content;

}
