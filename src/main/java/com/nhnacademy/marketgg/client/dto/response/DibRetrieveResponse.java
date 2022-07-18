package com.nhnacademy.marketgg.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DibRetrieveResponse {

    private Long productNo;

    private Byte[] thumbnail;

    private String productName;

    private String productPrice;

}
