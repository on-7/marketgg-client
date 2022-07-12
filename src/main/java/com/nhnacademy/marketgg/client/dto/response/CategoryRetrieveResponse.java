package com.nhnacademy.marketgg.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategoryRetrieveResponse {

    private String categorizationCode;

    private String categoryCode;

    private String categorizationName;

    private String categoryName;

    private Integer sequence;

}
