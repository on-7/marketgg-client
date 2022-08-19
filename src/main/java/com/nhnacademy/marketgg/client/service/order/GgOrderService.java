package com.nhnacademy.marketgg.client.service.order;

import com.nhnacademy.marketgg.client.dto.order.OrderCreateRequest;
import com.nhnacademy.marketgg.client.dto.order.OrderResponse;
import com.nhnacademy.marketgg.client.dto.response.DeliveryLocationResponseDto;
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

    /**
     * 관리자가 운송장 번호 만들기를 요청합니다.
     *
     * @param orderNo - 주문 번호
     */
    @Override
    public void createTrackingNo(final Long orderNo) {
        orderRepository.createTrackingNo(orderNo);
    }

    /**
     * 회원이 주문 내역에 있는 운송장 번호로 배송정보를 조회합니다.
     *
     * @param trackingNo - 운송장 번호
     * @return 배송정보 리스트
     */
    @Override
    public List<DeliveryLocationResponseDto> retrieveDeliveryInfo(final String trackingNo) {
        return orderRepository.retrieveDeliveryInfo(trackingNo);
    }

}
