package com.nhnacademy.marketgg.client.dto.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CategoryUpdateRequest {

    @NotBlank
    @Size(min = 1, max = 6)
    private final String categoryCode;

    @NotBlank
    @Size(min = 1, max = 20)
    private final String name;

    @NotNull
    private final Integer sequence;

}
