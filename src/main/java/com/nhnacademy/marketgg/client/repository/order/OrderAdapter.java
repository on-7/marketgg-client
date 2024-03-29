package com.nhnacademy.marketgg.client.repository.order;

import static com.nhnacademy.marketgg.client.util.GgUtils.SHOP_SERVICE_PREFIX_V1;
import static java.util.Collections.singletonList;

import com.nhnacademy.marketgg.client.dto.cart.CartOrderRequest;
import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.common.MemberInfo;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.delivery.DeliveryLocationResponseDto;
import com.nhnacademy.marketgg.client.dto.order.OrderCreateRequest;
import com.nhnacademy.marketgg.client.dto.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.order.OrderFormResponse;
import com.nhnacademy.marketgg.client.dto.order.OrderPaymentKey;
import com.nhnacademy.marketgg.client.dto.order.OrderRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.order.OrderToPayment;
import com.nhnacademy.marketgg.client.util.JwtUtils;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * {@inheritDoc}
 *
 * @author 이제훈
 */
@Component
@RequiredArgsConstructor
public class OrderAdapter implements OrderRepository {

    private static final String ORDERS_PATH_PREFIX = "/orders";
    private final String gatewayIp;

    @Value("${gg.delivery.origin}")
    private String deliveryHost;
    private static final String EGGPLANT_DELIVERY_PREFIX = "/eggplant-delivery";

    /**
     * {@inheritDoc}
     *
     * @param orderRequest - 주문 생성 시 필요한 요청 정보 객체
     * @return OrderToPayment
     */
    @Override
    public OrderToPayment createOrder(final OrderCreateRequest orderRequest, final MemberInfo memberinfo) {
        ResponseEntity<CommonResult<OrderToPayment>> response
            = WebClient.builder()
                       .baseUrl(gatewayIp)
                       .defaultHeaders(httpHeaders -> {
                           httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                           httpHeaders.setAccept(singletonList(MediaType.APPLICATION_JSON));
                           httpHeaders.setBearerAuth(JwtUtils.getToken());
                       })
                       .build()
                       .post()
                       .uri(SHOP_SERVICE_PREFIX_V1 + ORDERS_PATH_PREFIX)
                       .bodyValue(orderRequest)
                       .retrieve()
                       .toEntity(new ParameterizedTypeReference<CommonResult<OrderToPayment>>() {
                       })
                       .blockOptional()
                       .orElseThrow(RuntimeException::new);

        return Objects.requireNonNull(response.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     *
     * @return - 주문 목록
     */
    @Override
    public PageResult<OrderRetrieveResponse> retrieveOrders(final Integer page) {
        ResponseEntity<CommonResult<PageResult<OrderRetrieveResponse>>> response
            = WebClient.builder()
                       .baseUrl(gatewayIp)
                       .defaultHeaders(httpHeaders -> {
                           httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                           httpHeaders.setAccept(singletonList(MediaType.APPLICATION_JSON));
                           httpHeaders.setBearerAuth(JwtUtils.getToken());
                       })
                       .build()
                       .get()
                       .uri(SHOP_SERVICE_PREFIX_V1 + ORDERS_PATH_PREFIX + "?page=" + page)
                       .retrieve()
                       .toEntity(
                           new ParameterizedTypeReference<CommonResult<PageResult<OrderRetrieveResponse>>>() {
                           })
                       .blockOptional()
                       .orElseThrow(NullPointerException::new);

        return Objects.requireNonNull(response.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId - 주문 번호
     * @return 특정한 주문에 대한 상세 정보 응답 객체
     */
    @Override
    public OrderDetailRetrieveResponse retrieveOrder(final Long orderId) {
        ResponseEntity<CommonResult<OrderDetailRetrieveResponse>> response
            = WebClient.builder()
                       .baseUrl(gatewayIp)
                       .defaultHeaders(httpHeaders -> {
                           httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                           httpHeaders.setAccept(singletonList(MediaType.APPLICATION_JSON));
                           httpHeaders.setBearerAuth(JwtUtils.getToken());
                       })
                       .build()
                       .get()
                       .uri(SHOP_SERVICE_PREFIX_V1 + ORDERS_PATH_PREFIX + "/" + orderId)
                       .retrieve()
                       .toEntity(
                           new ParameterizedTypeReference<CommonResult<OrderDetailRetrieveResponse>>() {
                           })
                       .blockOptional()
                       .orElseThrow(NullPointerException::new);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId - 주문 번호
     */
    @Override
    public void cancelOrder(final Long orderId) {
        WebClient client = WebClient.builder()
                                    .baseUrl(gatewayIp)
                                    .defaultHeaders(
                                        headers -> headers.setAccept(singletonList(MediaType.APPLICATION_JSON))
                                    )
                                    .build();

        client.put()
              .uri(SHOP_SERVICE_PREFIX_V1 + ORDERS_PATH_PREFIX + "/" + orderId)
              .retrieve();
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId - 주문 번호
     */
    @Override
    public void createTrackingNo(final Long orderId) {
        WebClient client = WebClient.builder()
                                    .baseUrl(gatewayIp)
                                    .defaultHeaders(httpHeaders -> {
                                        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                                        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                                    })
                                    .build();

        client.patch()
                .uri(SHOP_SERVICE_PREFIX_V1 + ORDERS_PATH_PREFIX + "/" + orderId + "/delivery")
                .retrieve()
                .toEntity(Void.class)
                .block();
    }

    /**
     * {@inheritDoc}
     *
     * @param trackingNo - 운송장 번호
     * @return - 배송정보 리스트
     */
    @Override
    public List<DeliveryLocationResponseDto> retrieveDeliveryInfo(final String trackingNo) {
        WebClient client = WebClient.builder()
                                    .baseUrl(deliveryHost)
                                    .defaultHeaders(httpHeaders -> {
                                        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                                        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                                    })
                                    .build();

        return client.get()
                     .uri(uriBuilder -> uriBuilder.path(EGGPLANT_DELIVERY_PREFIX + "/tracking-no")
                                                  .queryParam("trackingNo", trackingNo)
                                                  .build())
                     .retrieve()
                     .bodyToMono(new ParameterizedTypeReference<List<DeliveryLocationResponseDto>>() {
                     })
                     .blockOptional()
                     .orElseThrow(NullPointerException::new);
    }

    @Override
    public OrderFormResponse retrieveOrderForm(CartOrderRequest cartRequest) {
        ResponseEntity<CommonResult<OrderFormResponse>> response
            = WebClient.builder()
                       .baseUrl(gatewayIp)
                       .defaultHeaders(httpHeaders -> {
                           httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                           httpHeaders.setAccept(singletonList(MediaType.APPLICATION_JSON));
                           httpHeaders.setBearerAuth(JwtUtils.getToken());
                       })
                       .build()
                       .post()
                       .uri(SHOP_SERVICE_PREFIX_V1 + ORDERS_PATH_PREFIX + "/order-form")
                       .bodyValue(cartRequest)
                       .retrieve()
                       .toEntity(
                           new ParameterizedTypeReference<CommonResult<OrderFormResponse>>() {
                           })
                       .blockOptional()
                       .orElseThrow(NullPointerException::new);

        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public OrderPaymentKey retrieveOrderPaymentKey(final Long orderId) {
        ResponseEntity<CommonResult<OrderPaymentKey>> response
                = WebClient.builder()
                           .baseUrl(gatewayIp)
                           .defaultHeaders(httpHeaders -> {
                               httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                               httpHeaders.setAccept(singletonList(MediaType.APPLICATION_JSON));
                               httpHeaders.setBearerAuth(JwtUtils.getToken());
                           })
                           .build()
                           .get()
                           .uri(SHOP_SERVICE_PREFIX_V1 + ORDERS_PATH_PREFIX + "/" + orderId + "/paymentKey")
                           .retrieve()
                           .toEntity(
                                   new ParameterizedTypeReference<CommonResult<OrderPaymentKey>>() {
                                   })
                           .blockOptional()
                           .orElseThrow(NullPointerException::new);

        return Objects.requireNonNull(response.getBody()).getData();
    }

}
