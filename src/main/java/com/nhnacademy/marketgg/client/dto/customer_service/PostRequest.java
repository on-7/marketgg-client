package com.nhnacademy.marketgg.client.dto.customer_service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PostRequest {

    @NotBlank
    @Size(min = 1, max = 6)
    private final String categoryCode;

    @NotBlank
    @Size(min = 1, max = 50)
    private final String title;

    @NotBlank
    @Size(min = 1)
    private final String content;

    @NotBlank
    @Size(min = 1, max = 100)
    private final String reason;

}
