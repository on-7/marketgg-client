package com.nhnacademy.marketgg.client.domain.dto.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 상품 수정을 위한 DTO 입니다.
 *
 * @version 1.0.0
 */
@AllArgsConstructor
@Getter
public class ProductModifyRequest {

    private String categoryCode;
    private String name;
    private String content;
    private Long totalStock;
    private Long price;
    private String description;
    private String unit;
    private String deliveryType;
    private String origin;
    private String packageType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    private String allergyInfo;
    private String capacity;

}
