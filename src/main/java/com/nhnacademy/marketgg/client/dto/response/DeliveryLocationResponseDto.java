package com.nhnacademy.marketgg.client.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 배송정보의 컬럼 값과 해당 배송정보의 위치 컬럼 값을 담고있는 ResponseDto 입니다.
 *
 * @author 김훈민, 조재철
 * @version 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
public class DeliveryLocationResponseDto {

    private String status;
    private LocalDateTime completionTime;
    private Long locationNo;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime arrivalTime;

}

