package com.nhnacademy.marketgg.client.service.order;

import com.nhnacademy.marketgg.client.dto.cart.CartOrderRequest;
import com.nhnacademy.marketgg.client.dto.common.MemberInfo;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.delivery.DeliveryLocationResponseDto;
import com.nhnacademy.marketgg.client.dto.order.OrderCreateRequest;
import com.nhnacademy.marketgg.client.dto.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.order.OrderFormResponse;
import com.nhnacademy.marketgg.client.dto.order.OrderPaymentKey;
import com.nhnacademy.marketgg.client.dto.order.OrderRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.order.OrderToPayment;
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
    public OrderToPayment createOrder(final OrderCreateRequest orderRequest, final MemberInfo memberInfo) {
        return orderRepository.createOrder(orderRequest, memberInfo);
    }

    /**
     * {@inheritDoc}
     *
     * @return 주문 목록이 담긴 응답 객체
     */
    @Override
    public PageResult<OrderRetrieveResponse> retrieveOrders(final Integer page) {
        return orderRepository.retrieveOrders(page);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId - 주문 번호
     * @return 주문 상세 정보 응답 객체
     */
    @Override
    public OrderDetailRetrieveResponse retrieveOrder(final Long orderId) {
        OrderDetailRetrieveResponse response = orderRepository.retrieveOrder(orderId);
        response.checkTrackingNo(response.getTrackingNo());
        response.checkCancel(response.getOrderStatus());

        return response;
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId - 주문 번호
     */
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

    /**
     * {@inheritDoc}
     *
     * @param cartRequest - 장바구니에 담긴 상품 목록
     * @return 주문서 작성에 필요한 정보를 담은 DTO 객체
     */
    @Override
    public OrderFormResponse retrieveOrderForm(final CartOrderRequest cartRequest) {
        return orderRepository.retrieveOrderForm(cartRequest);
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId - paymentKey 를 조회할 주문 식별 번호
     * @return paymentKey 정보를 담은 DTO 객체
     */
    @Override
    public OrderPaymentKey retrieveOrderPaymentKey(final Long orderId) {
        return orderRepository.retrieveOrderPaymentKey(orderId);
    }

}
