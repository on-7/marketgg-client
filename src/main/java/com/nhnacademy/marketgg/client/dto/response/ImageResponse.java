package com.nhnacademy.marketgg.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ImageResponse {

    private String name;

    private Long length;

    private String imageAddress;

    private Integer imageSequence;
}
