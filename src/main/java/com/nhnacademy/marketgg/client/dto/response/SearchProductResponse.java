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

    public SearchProductResponse(SearchProductResponse readValue) {
        this.id = readValue.getId();
        this.productName = readValue.getProductName();
        this.categoryCode = readValue.getCategoryCode();
        this.labelName = readValue.getLabelName();
        this.imageAddress = readValue.getImageAddress();
        this.price = readValue.getPrice();
        this.content = readValue.getContent();
    }

}
