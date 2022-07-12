package com.nhnacademy.marketgg.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategoryUpdateRequest {

    private String categorizationCode;

    private String name;

    private Integer sequence;

}
