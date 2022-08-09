package com.nhnacademy.marketgg.client.service.order;

import com.nhnacademy.marketgg.client.dto.order.OrderCreateRequest;
import com.nhnacademy.marketgg.client.repository.order.OrderRepository;
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

}