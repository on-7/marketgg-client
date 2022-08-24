package com.nhnacademy.marketgg.client.dto.order;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * 주문 요청에 대해 자체적으로 주문 데이터를 관리하고, 이후 결제 절차로 넘어갈 때 필요한 데이터를 전달하기 위한 클래스입니다.
 *
 * @author 김정민
 * @author 이제훈
 * @version 1.0.0
 * @since 1.0.0
 */
@NoArgsConstructor
@Getter
public class OrderToPayment {

    @NotBlank
    @Size(min = 6, max = 64)
    private String orderId;

    @NotBlank
    @Size(max = 100)
    private String orderName;

    @NotBlank
    @Size(max = 100)
    private String memberName;

    @NotBlank
    @Size(max = 100)
    private String memberEmail;

    @NotNull
    private Long totalAmount;

    @Nullable
    private Long couponId;

    @Nullable
    private Integer usedPoint;

    @Nullable
    private Integer expectedSavePoint;

}
