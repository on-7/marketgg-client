package com.nhnacademy.marketgg.client.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CategoryCreateRequest {

    @NotBlank
    @Size(min = 1, max = 6)
    private String categoryCode;

    @NotBlank
    @Size(min = 1, max = 3)
    private String categorizationCode;

    @NotBlank
    @Size(min = 1, max = 20)
    private String name;

    @NotNull
    private Integer sequence;

}
