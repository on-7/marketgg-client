package com.nhnacademy.marketgg.client.dto.response.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class SingleResponse<T> extends CommonResponse {

    private T data;

    public SingleResponse(T data) {
        super.success = true;
        this.data = data;
    }

}
