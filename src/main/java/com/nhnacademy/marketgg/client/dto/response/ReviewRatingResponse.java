package com.nhnacademy.marketgg.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewRatingResponse {

    private Long rating;

    private Long ratingCount;
}
