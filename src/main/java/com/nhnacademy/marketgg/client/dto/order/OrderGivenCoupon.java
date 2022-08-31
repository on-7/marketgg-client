package com.nhnacademy.marketgg.client.dto.order;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문서 작성 시 회원이 보유한 쿠폰 정보를 전달하기 위한 DTO 입니다.
 *
 * @author 김정민
 * @author 이제훈
 * @version 1.0.0
 * @since 1.0.0
 */
@NoArgsConstructor
@Getter
public class OrderGivenCoupon {

    @NotNull
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private Integer expiredDate;

    @NotNull
    private Integer minimumMoney;

    @NotNull
    private Double discountAmount;

}
