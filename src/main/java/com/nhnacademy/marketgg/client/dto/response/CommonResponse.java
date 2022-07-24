package com.nhnacademy.marketgg.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommonResponse<T> {

    protected boolean success;
    protected T data;
}
