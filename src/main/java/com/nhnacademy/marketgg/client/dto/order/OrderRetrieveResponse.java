package com.nhnacademy.marketgg.client.dto.order;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 목록 조회 시 필요한 정보를 전달하기 위한 DTO 입니다.
 *
 * @author 김정민
 * @version 1.0.0
 * @since 1.0.0
 */
@NoArgsConstructor
@Getter
public class OrderRetrieveResponse {

    private Long id;

    private Long memberId;

    private String orderName;

    private Long totalAmount;

    private String orderStatus;

    private LocalDateTime createdAt;

}
