package com.nhnacademy.marketgg.client.dto.product;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductInquiryResponse {

    private String name;

    private Long productId;

    private Long productInquiryNo;

    private String productName;

    private String title;

    private String content;

    private Boolean isSecret;

    private String adminReply;

    private LocalDateTime createdAt;

    private Boolean isReadable;

}
