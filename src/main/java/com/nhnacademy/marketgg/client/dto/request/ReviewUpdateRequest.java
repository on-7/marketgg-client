package com.nhnacademy.marketgg.client.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewUpdateRequest {

    private Long reviewId;

    private Long assetId;

    @Size(max = 300, message = "후기 내용은 300자를 넘을 수 없습니다.")
    private String content;

    @Max(value = 5, message = "평점은 5를 초과할 수 없습니다.")
    @Min(value = 1, message = "평점은 1 미만일 수 없습니다.")
    private Long rating;

}
