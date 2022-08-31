package com.nhnacademy.marketgg.client.dto.customer_service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PostStatusUpdateRequest {

    @NotBlank
    @Size(min = 1, max = 10)
    private final String status;

}
