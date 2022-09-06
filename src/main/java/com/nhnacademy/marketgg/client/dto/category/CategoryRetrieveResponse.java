package com.nhnacademy.marketgg.client.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Getter
public class CategoryRetrieveResponse implements Serializable {

    private String categoryCode;

    private String categorizationName;

    private String categoryName;

    private Integer sequence;

}
