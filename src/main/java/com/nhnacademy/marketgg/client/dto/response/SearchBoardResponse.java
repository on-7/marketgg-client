package com.nhnacademy.marketgg.client.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchBoardResponse {

    private Long id;

    private String categoryCode;

    private String title;

    private String reason;

    private String status;

    private LocalDateTime createdAt;

}
