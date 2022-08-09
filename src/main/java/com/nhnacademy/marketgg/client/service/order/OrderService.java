package com.nhnacademy.marketgg.client.service.order;

import com.nhnacademy.marketgg.client.dto.order.OrderCreateRequest;

/**
 * 주문에 대한 서비스 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
public interface OrderService {

    /**
     * 주문을 생성합니다.
     *
     * @param orderRequest - 주문 생성 시 필요한 요청 정보 객체
     */
    void createOrder(final OrderCreateRequest orderRequest);

}
