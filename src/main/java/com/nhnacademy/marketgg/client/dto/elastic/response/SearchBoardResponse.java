package com.nhnacademy.marketgg.client.dto.elastic.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchBoardResponse {

    private Long id;

    private String title;

    private String categoryCode;

    private String status;

    private String reason;

    private LocalDateTime createdAt;

}
