package com.nhnacademy.marketgg.client.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SearchRequest {

    private final String request;

    private final Integer page;

    private final Integer size;

}
