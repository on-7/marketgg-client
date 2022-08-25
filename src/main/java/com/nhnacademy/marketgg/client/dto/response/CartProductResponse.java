package com.nhnacademy.marketgg.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CartProductResponse {

    private Long id;
    private String name;
    private String thumbnail;
    private Integer amount;
    private Long price;

}
