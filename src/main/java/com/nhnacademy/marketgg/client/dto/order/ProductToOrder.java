package com.nhnacademy.marketgg.client.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 장바구니로부터 주문할 상품에 대한 정보를 전달하기 위한 DTO 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 * @since 1.0.0
 */
@NoArgsConstructor
@Getter
public class ProductToOrder {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Long price;

    @NotNull
    private Integer amount;

}
