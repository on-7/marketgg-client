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
import java.util.List;

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
     * @param orderRequest 주문 생성 시 필요한 요청 정보 객체
     */
    OrderToPayment createOrder(final OrderCreateRequest orderRequest, final MemberInfo memberInfo);

    /**
     * 주문 목록을 조회합니다.
     *
     * @return 주문 목록이 담긴 응답 객체
     */
    PageResult<OrderRetrieveResponse> retrieveOrders(final Integer page);

    /**
     * 주문 상세 조회를 처리합니다.
     *
     * @param orderId 주문 번호
     * @return 주문 상세 정보 응답 객체
     */
    OrderDetailRetrieveResponse retrieveOrder(final Long orderId);

    /**
     * 주문 취소를 처리합니다.
     *
     * @param orderId 주문 번호
     */
    void cancelOrder(final Long orderId);

    /**
     * 운송장 번호 생성을 요청합니다.
     *
     * @param orderNo 주문 번호
     */
    void createTrackingNo(final Long orderNo);

    /**
     * 배송 정보 내역 조회를 요청합니다.
     *
     * @param trackingNo 운송장 번호
     * @return 운송장 번호에 해당하는 배송 정보 목록
     */
    List<DeliveryLocationResponseDto> retrieveDeliveryInfo(final String trackingNo);

    /**
     * 주문하기 전 주문서 작성에 필요한 정보를 조회하는 요청을 수행합니다.
     *
     * @param cartRequest 장바구니에 담긴 상품 목록
     * @return 주문서를 구성하는 주문 상품 정보
     */
    OrderFormResponse retrieveOrderForm(final CartOrderRequest cartRequest);

    /**
     * 주문에 대한 paymentKey 정보를 조회합니다.
     *
     * @param orderId - paymentKey 정보를 조회할 주문 번호
     * @return 주문에 대한 paymentKey
     */
    OrderPaymentKey retrieveOrderPaymentKey(final Long orderId);
}
