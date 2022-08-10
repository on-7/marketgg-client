package com.nhnacademy.marketgg.client.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SearchRequest {

    @NotBlank
    @Size(min = 1, max = 30)
    private final String keyword;

    @NotNull
    @Min(0)
    private final Integer page;

    @NotNull
    @Min(1)
    private final Integer size;

}
