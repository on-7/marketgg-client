package com.nhnacademy.marketgg.client.service.order;

import com.nhnacademy.marketgg.client.dto.order.OrderCreateRequest;
import com.nhnacademy.marketgg.client.dto.order.OrderResponse;
import com.nhnacademy.marketgg.client.repository.order.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 주문 서비스 구현 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class GgOrderService implements OrderService {

    private final OrderRepository orderRepository;

    /**
     * {@inheritDoc}
     *
     * @param orderRequest - 주문 생성 시 필요한 요청 정보 객체
     */
    @Override
    public void createOrder(final OrderCreateRequest orderRequest) {
        orderRepository.createOrder(orderRequest);
    }

    /**
     * {@inheritDoc}
     *
     * @return 주문 목록이 담긴 응답 객체
     */
    @Override
    public List<OrderResponse> retrieveOrders() {
        return orderRepository.retrieveOrders();
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId - 주문 번호
     * @return 주문 상세 정보 응답 객체
     */
    @Override
    public OrderResponse retrieveOrder(final Long orderId) {
        return orderRepository.retrieveOrder(orderId);
    }

    @Override
    public void cancelOrder(final Long orderId) {
        orderRepository.cancelOrder(orderId);
    }

}