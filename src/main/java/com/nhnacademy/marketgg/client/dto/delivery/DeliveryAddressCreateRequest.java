package com.nhnacademy.marketgg.client.dto.delivery;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class DeliveryAddressCreateRequest {

    private final Boolean defaultAddress;

    @NotNull(message = "우편번호는 null 일 수 없습니다.")
    @Positive(message = "우편번호는 음수 일 수 없습니다.")
    private final Integer zipcode;

    @NotBlank(message = "주소를 입력해 주세요.")
    @Size(min = 5, max = 100)
    private final String address;

    @NotBlank(message = "상세 주소를 입력해 주세요.")
    @Size(min = 1, max = 100)
    private final String detailAddress;

    public DeliveryAddressCreateRequest(Boolean defaultAddress, Integer zipcode, String address, String detailAddress) {
        if (Objects.isNull(defaultAddress)) {
            defaultAddress = Boolean.FALSE;
        }
        this.defaultAddress = defaultAddress;
        this.zipcode = zipcode;
        this.address = address;
        this.detailAddress = detailAddress;
    }

}
