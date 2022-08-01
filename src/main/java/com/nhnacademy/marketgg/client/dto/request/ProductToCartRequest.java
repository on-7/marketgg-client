package com.nhnacademy.marketgg.client.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductToCartRequest {

    @NotNull
    private final Long id;

    @NotNull
    @Max(value = 999) @Min(value = 1)
    private final Integer amount;

}
