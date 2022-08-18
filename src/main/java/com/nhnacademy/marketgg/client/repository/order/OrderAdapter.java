package com.nhnacademy.marketgg.client.repository.order;

import static java.util.Collections.singletonList;

import com.nhnacademy.marketgg.client.dto.order.OrderCreateRequest;
import com.nhnacademy.marketgg.client.dto.order.OrderResponse;
import com.nhnacademy.marketgg.client.util.GgUrlUtils;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
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

    private final String gatewayIp;

    /**
     * {@inheritDoc}
     *
     * @param orderRequest - 주문 생성 시 필요한 요청 정보 객체
     */
    @Override
    public void createOrder(final OrderCreateRequest orderRequest) {
        WebClient client = WebClient.builder()
                                    .baseUrl(gatewayIp)
                                    .defaultHeaders(httpHeaders -> {
                                        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                                        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                                    })
                                    .build();

        client.post()
              .uri(GgUrlUtils.SHOP_SERVICE_PREFIX_V1 + GgUrlUtils.ORDERS_PATH_PREFIX)
              .bodyValue(orderRequest)
              .retrieve();
    }

    /**
     * {@inheritDoc}
     *
     * @return - 주문 목록
     */
    @Override
    public List<OrderResponse> retrieveOrders() {
        WebClient client = WebClient.builder()
                                    .baseUrl(gatewayIp)
                                    .defaultHeaders(
                                        headers -> headers.setAccept(singletonList(MediaType.APPLICATION_JSON))
                                    )
                                    .build();

        return client.get()
                     .uri(GgUrlUtils.SHOP_SERVICE_PREFIX_V1 + GgUrlUtils.ORDERS_PATH_PREFIX)
                     .retrieve()
                     .bodyToMono(new ParameterizedTypeReference<List<OrderResponse>>() {
                     })
                     .blockOptional().orElseThrow();
    }

    /**
     * {@inheritDoc}
     *
     * @param orderId - 주문 번호
     * @return 특정한 주문에 대한 상세 정보 응답 객체
     */
    @Override
    public OrderResponse retrieveOrder(Long orderId) {
        WebClient client = WebClient.builder()
                                    .baseUrl(gatewayIp)
                                    .defaultHeaders(
                                        headers -> headers.setAccept(singletonList(MediaType.APPLICATION_JSON))
                                    )
                                    .build();

        return client.get()
                     .uri(GgUrlUtils.SHOP_SERVICE_PREFIX_V1 + GgUrlUtils.ORDERS_PATH_PREFIX + "/" + orderId)
                     .retrieve()
                     .bodyToMono(OrderResponse.class)
                     .blockOptional().orElseThrow();
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
              .uri(GgUrlUtils.SHOP_SERVICE_PREFIX_V1 + GgUrlUtils.ORDERS_PATH_PREFIX + "/" + orderId)
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
              .uri(GgUrlUtils.SHOP_SERVICE_PREFIX_V1 + GgUrlUtils.ORDERS_PATH_PREFIX + "/" + orderId)
              .retrieve();
    }

}
