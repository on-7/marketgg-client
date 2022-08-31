package com.nhnacademy.marketgg.client.dto.delivery;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 수정된 배송정보 전송을 위한 Dto 입니다.
 *
 * @author 김훈민, 조재철
 * @version 1.0.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryInfoStatusRequestDto {

    @NotBlank(message = "운송장 번호는 필수 사항 입니다.")
    private String orderNo;

    @NotNull(message = "배송 상태는 필수 사항 입니다.")
    @Size(min = 36, max = 36)
    private String status;

    private LocalDateTime arrivalTime;

}
