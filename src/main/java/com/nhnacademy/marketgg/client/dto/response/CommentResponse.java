package com.nhnacademy.marketgg.client.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentResponse {

    private String content;

    private String email;

    private LocalDateTime createdAt;

}
