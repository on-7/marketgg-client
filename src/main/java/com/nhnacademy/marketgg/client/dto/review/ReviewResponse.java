package com.nhnacademy.marketgg.client.dto.review;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewResponse {

    private Long id;

    private Long memberId;

    private String memberName;

    private Long assetId;

    private String content;

    private Long rating;

    private Boolean isBest;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}
