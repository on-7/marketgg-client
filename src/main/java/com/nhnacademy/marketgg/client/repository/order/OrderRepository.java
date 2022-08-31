package com.nhnacademy.marketgg.client.repository.order;

import com.nhnacademy.marketgg.client.dto.common.MemberInfo;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.order.OrderCreateRequest;
import com.nhnacademy.marketgg.client.dto.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.order.OrderFormResponse;
import com.nhnacademy.marketgg.client.dto.order.OrderRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.order.OrderToPayment;
import com.nhnacademy.marketgg.client.dto.cart.CartOrderRequest;
import com.nhnacademy.marketgg.client.dto.delivery.DeliveryLocationResponseDto;
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
     * @param orderRequest 주문 생성 시 필요한 요청 정보 객체
     * @return 결제에 필요한 정보가 담겨있는 {@link OrderToPayment} 객체
     */
    OrderToPayment createOrder(final OrderCreateRequest orderRequest, MemberInfo memberInfo);

    /**
     * 주문 목록을 조회합니다.
     *
     * @return 주문 목록 응답 객체
     */
    PageResult<OrderRetrieveResponse> retrieveOrders(final Integer page);

    /**
     * 주문 상세 정보를 조회합니다.
     *
     * @param orderId 주문 번호
     * @return 특정한 주문에 대한 상세 정보 응답 객체
     */
    OrderDetailRetrieveResponse retrieveOrder(final Long orderId);

    /**
     * 주문을 취소한 상태로 처리합니다.
     *
     * @param orderId 주문 번호
     */
    void cancelOrder(final Long orderId);

    /**
     * 관리자가 운송장 번호를 만듭니다.
     *
     * @param orderId 주문 번호
     */
    void createTrackingNo(final Long orderId);

    /**
     * 회원이 주문 내역에 있는 운송장 번호로 배송정보를 조회합니다.
     *
     * @param trackingNo 운송장 번호
     * @return 배송정보 리스트
     */
    List<DeliveryLocationResponseDto> retrieveDeliveryInfo(final String trackingNo);

    /**
     * 주문서 작성에 필요한 정보를 조회하는 API 를 요청합니다.
     *
     * @param cartResponse 장바구니에 담긴 상품 목록
     * @return 주문서를 구성하는 주문 상품 정보
     */
    OrderFormResponse retrieveOrderForm(final CartOrderRequest cartResponse);

}
