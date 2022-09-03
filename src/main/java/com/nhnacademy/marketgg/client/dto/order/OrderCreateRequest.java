package com.nhnacademy.marketgg.client.dto.order;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * 주문 생성 요청에 대한 정보를 담고 있는 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Getter
public class OrderCreateRequest {

    @NotBlank
    @Size(max = 100)
    private final String name;

    @NotBlank
    @Size(max = 100)
    private final String email;

    @NotNull
    private final Long deliveryAddressId;

    @NotNull
    private final List<Long> productIds;

    @Nullable
    private final String selectedCouponName;

    @Nullable
    private final Integer usedPoint;

    @NotNull
    private final Long totalAmount;

    private Integer trackingNo;

    @NotBlank
    @Size(min = 2, max = 4)
    private final String paymentType;

    private Integer expectedSavePoint;

}
