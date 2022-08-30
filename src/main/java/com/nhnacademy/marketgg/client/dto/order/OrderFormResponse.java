package com.nhnacademy.marketgg.client.dto.order;

import com.nhnacademy.marketgg.client.dto.response.DeliveryAddressResponse;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문서 입력 폼 출력을 위해 취합한 정보를 전달하기 위한 DTO 입니다.
 *
 * @author 김정민
 * @author 이제훈
 * @version 1.0.0
 * @since 1.0.0
 */
@NoArgsConstructor
@Getter
public class OrderFormResponse {

    @NotNull
    private List<ProductToOrder> products;

    @NotNull
    private Long memberId;

    @NotBlank
    @Size(max = 100)
    private String memberName;

    @NotBlank
    @Size(max = 20)
    private String memberEmail;

    @NotBlank
    private String memberGrade;

    @NotNull
    private List<OrderGivenCoupon> givenCouponList;

    @NotNull
    private Integer totalPoint;

    @NotNull
    private List<DeliveryAddressResponse> deliveryAddressList;

    @NotNull
    private List<String> paymentType;

    @NotNull
    private Long totalOrigin;

}
