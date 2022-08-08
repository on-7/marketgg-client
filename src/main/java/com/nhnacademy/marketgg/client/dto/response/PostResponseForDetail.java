package com.nhnacademy.marketgg.client.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostResponseForDetail {

    private Long id;

    private String categoryCode;

    private String title;

    private String content;

    private String reason;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<CommentResponse> commentList;

}
