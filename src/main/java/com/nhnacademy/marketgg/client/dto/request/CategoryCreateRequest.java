package com.nhnacademy.marketgg.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryCreateRequest {

    private String categoryCode;

    private String categorizationCode;

    private String name;

    private Integer sequence;

}
