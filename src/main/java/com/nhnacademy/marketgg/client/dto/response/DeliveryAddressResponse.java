package com.nhnacademy.marketgg.client.dto.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 배송지 정보를 담은 응답 객체입니다.
 *
 * @author 김훈민
 * @author 이제훈
 * @since 1.0.0
 */
@NoArgsConstructor
@Getter
public class DeliveryAddressResponse {

    @NotNull
    private Long id;

    @NotNull
    private Boolean isDefaultAddress;

    @NotNull
    private Integer zipcode;

    @NotBlank
    @Size(max = 100)
    private String address;

    @NotBlank
    @Size(max = 100)
    private String detailAddress;

}
