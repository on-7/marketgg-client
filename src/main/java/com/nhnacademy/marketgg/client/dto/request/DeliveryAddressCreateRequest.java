package com.nhnacademy.marketgg.client.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeliveryAddressCreateRequest {

    @NotNull
    private final boolean defaultAddress;

    @NotNull
    @Positive
    private final Integer zipcode;

    @NotBlank
    @Size(min = 5, max = 100)
    private final String address;

    @NotBlank
    @Size(min = 1, max = 100)
    private final String detailAddress;

}
