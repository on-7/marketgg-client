package com.nhnacademy.marketgg.client.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProductToCartRequest {

    // 상품 ID
    @NotNull
    private final Long id;

    @Max(value = 999)
    @Min(value = 1)
    private final Integer amount;

}
