package com.nhnacademy.marketgg.client.repository.delivery;

import com.nhnacademy.marketgg.client.dto.request.CreatedTrackingNoRequest;
import com.nhnacademy.marketgg.client.dto.request.DeliveryInfoStatusRequestDto;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * {@inheritDoc}
 *
 * @author 김훈민
 */
@Component
@RequiredArgsConstructor
public class DeliveryAdaptor implements DeliveryRepository {

    public static final String SHOP_SERVICE_PREFIX_V1 = "/shop/v1";
    public static final String ORDERS_PATH_PREFIX = "/orders";

    private final String gatewayIp;

    /**
     * {@inheritDoc}
     *
     * @param deliveryInfoStatusRequestDto - 배송정보의 상태를 담은 객체 입니다.
     */
    @Override
    public void updateDeliveryInfoStatus(final DeliveryInfoStatusRequestDto deliveryInfoStatusRequestDto) {
        WebClient client = WebClient.builder()
                                    .baseUrl(gatewayIp)
                                    .defaultHeaders(httpHeaders -> {
                                        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                                        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                                    })
                                    .build();

        client.patch()
              .uri(SHOP_SERVICE_PREFIX_V1 + ORDERS_PATH_PREFIX + "/" + deliveryInfoStatusRequestDto.getOrderNo())
              .bodyValue(DeliveryInfoStatusRequestDto.builder()
                                                     .status(deliveryInfoStatusRequestDto.getStatus())
                                                     .build())
              .retrieve();
    }

    @Override
    public void createTrackingNo(final CreatedTrackingNoRequest createdTrackingNoRequest) {
        WebClient client = WebClient.builder()
                                    .baseUrl(gatewayIp)
                                    .defaultHeaders(httpHeaders -> {
                                        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                                        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                                    })
                                    .build();

        client.post()
              .uri(SHOP_SERVICE_PREFIX_V1 + ORDERS_PATH_PREFIX + "/" + createdTrackingNoRequest.getOrderNo()
                  + "/delivery")
              .bodyValue(CreatedTrackingNoRequest.builder()
                                                 .build())
              .retrieve();
    }

}
