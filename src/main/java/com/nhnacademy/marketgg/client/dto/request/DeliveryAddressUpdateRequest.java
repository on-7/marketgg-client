package com.nhnacademy.marketgg.client.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class DeliveryAddressUpdateRequest {

    // 배송지 Id
    @NotNull
    private Long id;

    @NotNull
    private boolean isDefaultAddress;

    @NotNull
    @Positive
    private Integer zipCode;

    @NotBlank
    @Size(min = 5, max = 100)
    private String address;

    @NotBlank
    @Size(min = 1, max = 100)
    private String detailAddress;
}
