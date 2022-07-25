package com.nhnacademy.marketgg.client.dto.request.searchutil;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Query {

    private MultiMatch multi_match;

}
