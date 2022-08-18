package com.nhnacademy.marketgg.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 배송지 정보를 담은 dto 객체 입니다.
 *
 * @author 김훈민
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public class DeliveryAddressResponse {

    private final Long id;
    private final Boolean isDefaultAddress;
    private final Integer zipCode;
    private final String address;
    private final String detailAddress;

}
