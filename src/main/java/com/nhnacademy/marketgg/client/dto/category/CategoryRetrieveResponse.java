package com.nhnacademy.marketgg.client.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategoryRetrieveResponse {

    private String categoryCode;

    private String categorizationName;

    private String categoryName;

    private Integer sequence;

}
