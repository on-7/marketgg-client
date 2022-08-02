package com.nhnacademy.marketgg.client.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PostRequest {

    private final String categoryCode;

    private final String title;

    private final String content;

    private final String reason;

}
