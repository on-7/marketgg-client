package com.nhnacademy.marketgg.client.repository.order;

import com.nhnacademy.marketgg.client.dto.order.OrderCreateRequest;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
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

        client.post().uri("/shop/v1/orders")
              .bodyValue(orderRequest)
              .retrieve();
    }

}
