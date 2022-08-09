package com.nhnacademy.marketgg.client.repository.order;

import com.nhnacademy.marketgg.client.dto.order.OrderCreateRequest;
import com.nhnacademy.marketgg.client.dto.order.OrderResponse;
import java.util.List;

/**
 * 주문에 대한 저장소 인터페이스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
public interface OrderRepository {

    /**
     * 주문을 생성합니다.
     *
     * @param orderRequest - 주문 생성 시 필요한 요청 정보 객체
     */
    void createOrder(final OrderCreateRequest orderRequest);

    /**
     * 주문 목록을 조회합니다.
     *
     * @return 주문 목록 응답 객체
     */
    List<OrderResponse> retrieveOrders();

    /**
     * 주문 상세 정보를 조회합니다.
     *
     * @param orderId - 주문 번호
     * @return 특정한 주문에 대한 상세 정보 응답 객체
     */
    OrderResponse retrieveOrder(Long orderId);

}
