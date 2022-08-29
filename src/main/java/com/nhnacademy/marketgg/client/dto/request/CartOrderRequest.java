package com.nhnacademy.marketgg.client.dto.request;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartOrderRequest {

    private final List<Long> id;

}
