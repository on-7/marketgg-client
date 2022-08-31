package com.nhnacademy.marketgg.client.dto.review;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReviewCreateRequest {

    @NotNull(message = "content는 null일수 없습니다.")
    @Size(min = 10, max = 300, message = "후기 내용은 10자 이상, 300자 이하만 가능합니다.")
    private final String content;

    @NotNull(message = "rating은 null일수 없습니다.")
    @Max(value = 5, message = "평점은 5를 초과할 수 없습니다.")
    @Min(value = 1, message = "평점은 1 미만일 수 없습니다.")
    private final Long rating;
}
