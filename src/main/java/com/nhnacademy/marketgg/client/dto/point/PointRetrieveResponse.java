package com.nhnacademy.marketgg.client.dto.point;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PointRetrieveResponse {

    private Long memberId;

    private Long orderNo;

    private Integer point;

    private Integer totalPoint;

    private String content;

    private LocalDateTime updatedAt;

}
