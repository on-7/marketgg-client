package com.nhnacademy.marketgg.client.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 상품 수정을 위한 DTO 입니다.
 *
 * @author 조현진
 */
@RequiredArgsConstructor
@Getter
public class ProductUpdateRequest {

    @NotNull(message = "자원번호는 null일수 없습니다.")
    private final Long assetNo;
    @NotBlank(message = "카테고리는 null일수 없습니다.")
    private final String categoryCode;
    @NotNull(message = "라벨번호는 null일수 없습니다.")
    private final Long labelNo;
    @NotBlank(message = "제대로된 값을 입력해주세요. 상품 이름은 null, 공백일 수 없습니다.")
    private final String name;
    @NotBlank(message = "제대로된 값을 입력해주세요. content는 null, 공백일 수 없습니다.")
    private final String content;
    @NotNull(message = "재고는 null일수 없습니다.")
    private final Long totalStock;
    @NotNull(message = "가격은 null일수 없습니다.")
    private final Long price;
    @NotBlank(message = "제대로된 값을 입력해주세요. 상품설명은 null, 공백일 수 없습니다.")
    private final String description;
    @NotBlank(message = "제대로된 값을 입력해주세요. 단위는 null, 공백일 수 없습니다.")
    private final String unit;
    @NotBlank(message = "제대로된 값을 입력해주세요. 배송타입은 null, 공백일 수 없습니다.")
    private final String deliveryType;
    @NotBlank(message = "제대로된 값을 입력해주세요. 원산지는 null, 공백일 수 없습니다.")
    private final String origin;
    @NotBlank(message = "제대로된 값을 입력해주세요. 포장 종류는 null, 공백일 수 없습니다.")
    private final String packageType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "유통기한은 null일수 없습니다.")
    private final LocalDate expirationDate;
    @NotBlank(message = "제대로된 값을 입력해주세요. 알러지정보는 null, 공백일 수 없습니다.")
    private final String allergyInfo;
    @NotBlank(message = "제대로된 값을 입력해주세요. 용량은 null, 공백일 수 없습니다.")
    private final String capacity;

}
