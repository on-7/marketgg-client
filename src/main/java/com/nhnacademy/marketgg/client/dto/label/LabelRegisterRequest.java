package com.nhnacademy.marketgg.client.dto.label;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LabelRegisterRequest {

    @NotBlank
    @Size(min = 1, max = 30)
    private String name;

}
