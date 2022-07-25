package com.nhnacademy.marketgg.client.dto.response.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ErrorEntity extends CommonResponse {

    private String message;

}
