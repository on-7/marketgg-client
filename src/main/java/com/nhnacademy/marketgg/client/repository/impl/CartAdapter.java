package com.nhnacademy.marketgg.client.repository.impl;

import com.nhnacademy.marketgg.client.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.client.repository.CartRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 장바구니 데이터를 처리하는 구현체입니다.
 * {@link com.nhnacademy.marketgg.client.repository.CartRepository}
 *
 * @author 윤동열
 */
@Component
@RequiredArgsConstructor
public class CartAdapter implements CartRepository {

    private static final String CART = "/shop/v1/cart";

    private final String gatewayIp;
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<String> addToCart(final ProductToCartRequest productAddRequest) {

        HttpEntity<ProductToCartRequest> httpEntity = new HttpEntity<>(productAddRequest, getJsonHeader());

        return restTemplate.postForEntity(gatewayIp + CART, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> retrieveCart() {
        HttpEntity<Void> httpEntity = new HttpEntity<>(getJsonHeader());

        return restTemplate.exchange(gatewayIp + CART, HttpMethod.GET, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> updateAmount(final ProductToCartRequest productUpdateRequest) {

        HttpEntity<ProductToCartRequest> httpEntity = new HttpEntity<>(productUpdateRequest, getJsonHeader());
        return restTemplate.exchange(gatewayIp + CART, HttpMethod.PATCH, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> delete(final List<Long> products) {

        HttpEntity<List<Long>> httpEntity = new HttpEntity<>(products, getJsonHeader());

        return restTemplate.exchange(gatewayIp + CART, HttpMethod.DELETE, httpEntity, String.class);
    }

    private HttpHeaders getJsonHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return header;
    }

}
