package com.nhnacademy.marketgg.client.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PostStatusUpdateRequest {

    private final String status;

}
