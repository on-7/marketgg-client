package com.nhnacademy.marketgg.client.repository.order;

import static com.nhnacademy.marketgg.client.util.GgUtils.SHOP_SERVICE_PREFIX_V1;
import static java.util.Collections.singletonList;

import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.ShopResult;
import com.nhnacademy.marketgg.client.dto.order.OrderCreateRequest;
import com.nhnacademy.marketgg.client.dto.order.OrderDetailRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.order.OrderRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.order.OrderToPayment;
import com.nhnacademy.marketgg.client.dto.order.ProductToOrder;
import com.nhnacademy.marketgg.client.dto.response.DeliveryLocationResponseDto;
import com.nhnacademy.marketgg.client.util.JwtUtils;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
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

    /**
     * {@inheritDoc}
     *
     * @param orderRequest - 주문 생성 시 필요한 요청 정보 객체
     * @return OrderToPayment
     */
    @Override
    public OrderToPayment createOrder(final OrderCreateRequest orderRequest, MemberInfo memberinfo) {
        ResponseEntity<ShopResult<OrderToPayment>> response
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
                       .toEntity(
                           new ParameterizedTypeReference<ShopResult<OrderToPayment>>() {
                           })
                       .blockOptional()
                       .orElseThrow(NullPointerException::new);

        URI location = response.getHeaders().getLocation();
        return Objects.requireNonNull(response.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     *
     * @return - 주문 목록
     */
    @Override
    public List<OrderRetrieveResponse> retrieveOrders() {
        ResponseEntity<ShopResult<List<OrderRetrieveResponse>>> response
                = WebClient.builder()
                           .baseUrl(gatewayIp)
                           .defaultHeaders(httpHeaders -> {
                               httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                               httpHeaders.setAccept(singletonList(MediaType.APPLICATION_JSON));
                               httpHeaders.setBearerAuth(JwtUtils.getToken());
                           })
                           .build()
                           .get()
                           .uri(SHOP_SERVICE_PREFIX_V1 + ORDERS_PATH_PREFIX)
                           .retrieve()
                           .toEntity(
                                   new ParameterizedTypeReference<ShopResult<List<OrderRetrieveResponse>>>() {
                                   })
                           .blockOptional()
                           .orElseThrow(NullPointerException::new);

        URI location = response.getHeaders().getLocation();
        return Objects.requireNonNull(response.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId - 주문 번호
     * @return 특정한 주문에 대한 상세 정보 응답 객체
     */
    @Override
    public OrderDetailRetrieveResponse retrieveOrder(Long orderId) {
        ResponseEntity<ShopResult<OrderDetailRetrieveResponse>> response
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
                                   new ParameterizedTypeReference<ShopResult<OrderDetailRetrieveResponse>>() {
                                   })
                           .blockOptional()
                           .orElseThrow(NullPointerException::new);

        URI location = response.getHeaders().getLocation();
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
              .uri(SHOP_SERVICE_PREFIX_V1 + ORDERS_PATH_PREFIX + "/" + orderId)
              .retrieve();
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
                                    .baseUrl(gatewayIp)
                                    .defaultHeaders(httpHeaders -> {
                                        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                                        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                                    })
                                    .build();

        return client.get()
                     .uri("localhost:9090/" + trackingNo)
                     .retrieve()
                     .bodyToMono(new ParameterizedTypeReference<List<DeliveryLocationResponseDto>>() {
                     })
                     .blockOptional().orElseThrow();
    }

}
