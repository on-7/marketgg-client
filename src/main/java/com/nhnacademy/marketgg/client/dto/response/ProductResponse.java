package com.nhnacademy.marketgg.client.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품의 모든 속성을 담은 반환 객체입니다.
 * deletedAt 속성만 nullable 입니다.
 * deletedAt 속성이 null이 아닌 경우 해당 상품은 삭제된 상태입니다.
 *
 * @version 1.0.0
 */
@NoArgsConstructor
@Getter
public class ProductResponse {

    private Long id;
    private Long assetNo;
    private String categoryCode;
    private String categoryName;
    private String name;
    private String content;
    private Long totalStock;
    private Long price;
    private String description;
    private String unit;
    private String deliveryType;
    private String origin;
    private String packageType;
    private LocalDate expirationDate;
    private String allergyInfo;
    private String capacity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
