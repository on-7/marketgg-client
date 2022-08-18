package com.nhnacademy.marketgg.client.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductInquiryResponse {

    private String uuid;

    private String name;

    private Long productId;

    private String title;

    private String content;

    private Boolean isSecret;

    private String adminReply;

    private LocalDateTime createdAt;

}