package com.nhnacademy.marketgg.client.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReviewUpdateRequest {

    @NotNull(message = "후기 번호는 null일 수 없습니다.")
    private final Long reviewId;

    @NotNull(message = "자원 번호는 null일 수 없습니다.")
    private final Long assetId;

    @Size(max = 300, message = "후기 내용은 300자를 넘을 수 없습니다.")
    private final String content;

    @Max(value = 5, message = "평점은 5를 초과할 수 없습니다.")
    @Min(value = 1, message = "평점은 1 미만일 수 없습니다.")
    private final Long rating;

}
