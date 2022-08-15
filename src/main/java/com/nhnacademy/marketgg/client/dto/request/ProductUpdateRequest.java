package com.nhnacademy.marketgg.client.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 수정을 위한 DTO 입니다.
 *
 * @author 조현진
 */
@NoArgsConstructor
@Getter
public class ProductUpdateRequest {

    @NotNull(message = "자원번호는 null일수 없습니다.")
    private Long assetNo;
    @NotBlank(message = "카테고리는 null일수 없습니다.")
    private String categoryCode;
    @NotNull(message = "라벨번호는 null일수 없습니다.")
    private Long labelNo;
    @NotBlank(message = "상품명은 null일수 없습니다.")
    private String name;
    @NotBlank(message = "content는 null일수 없습니다.")
    private String content;
    @NotNull(message = "재고는 null일수 없습니다.")
    private Long totalStock;
    @NotNull(message = "가격은 null일수 없습니다.")
    private Long price;
    @NotBlank(message = "상품 설명은 null일수 없습니다.")
    private String description;
    @NotBlank(message = "unit은 null일수 없습니다.")
    private String unit;
    @NotBlank(message = "배송 타입은 null일수 없습니다.")
    private String deliveryType;
    @NotBlank(message = "원산지는 null일수 없습니다.")
    private String origin;
    @NotBlank(message = "포장 타입은 null일수 없습니다.")
    private String packageType;
    @NotNull(message = "유통기한은 null일수 없습니다.")
    private LocalDate expirationDate;
    @NotBlank(message = "알러지 정보는 null일수 없습니다.")
    private String allergyInfo;
    @NotBlank(message = "capacity는 null일수 없습니다.")
    private String capacity;

}