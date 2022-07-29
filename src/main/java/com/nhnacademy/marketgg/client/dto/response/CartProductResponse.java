package com.nhnacademy.marketgg.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CartProductResponse {

    private Long id;
    private String name;
    private Integer amount;
    private Long price;

}
