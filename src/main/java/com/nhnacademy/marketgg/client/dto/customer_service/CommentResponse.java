package com.nhnacademy.marketgg.client.dto.customer_service;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentResponse {

    private String content;

    private String name;

    private LocalDateTime createdAt;

}
