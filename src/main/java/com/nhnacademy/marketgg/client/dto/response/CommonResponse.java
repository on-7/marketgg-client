package com.nhnacademy.marketgg.client.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class CommonResponse {

    protected final boolean success;
}
