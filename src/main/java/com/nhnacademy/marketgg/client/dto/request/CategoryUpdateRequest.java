package com.nhnacademy.marketgg.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryUpdateRequest {

    private String categoryCode;

    private String name;

    private Integer sequence;

}
