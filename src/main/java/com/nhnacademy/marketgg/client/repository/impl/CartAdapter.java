package com.nhnacademy.marketgg.client.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.client.dto.response.CartProductResponse;
import com.nhnacademy.marketgg.client.dto.response.common.ListResponse;
import com.nhnacademy.marketgg.client.dto.response.common.ResponseUtils;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
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
 *
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
    private final ObjectMapper mapper;

    @Override
    public void addToCart(final ProductToCartRequest productAddRequest)
        throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {
        HttpEntity<ProductToCartRequest> httpEntity = new HttpEntity<>(productAddRequest, getJsonHeader());

        ResponseEntity<String> response =
            restTemplate.postForEntity(gatewayIp + CART, httpEntity, String.class);

        ResponseUtils.checkError(response, mapper);

    }

    @Override
    public List<CartProductResponse> retrieveCart()
        throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        HttpEntity<Void> httpEntity = new HttpEntity<>(getJsonHeader());
        ResponseEntity<String> response =
            restTemplate.exchange(gatewayIp + CART, HttpMethod.GET, httpEntity, String.class);

        ResponseUtils.checkError(response, mapper);

        ListResponse<CartProductResponse> listResponse = mapper.readValue(response.getBody(), new TypeReference<>() {
        });

        return listResponse.getData();
    }

    @Override
    public void updateAmount(final ProductToCartRequest productUpdateRequest)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        HttpEntity<ProductToCartRequest> httpEntity = new HttpEntity<>(productUpdateRequest, getJsonHeader());
        ResponseEntity<String> response =
            restTemplate.exchange(gatewayIp + CART, HttpMethod.PATCH, httpEntity, String.class);

        ResponseUtils.checkError(response, mapper);
    }

    @Override
    public void delete(final List<Long> products)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        HttpEntity<List<Long>> httpEntity = new HttpEntity<>(products, getJsonHeader());
        ResponseEntity<String> response =
            restTemplate.exchange(gatewayIp + CART, HttpMethod.DELETE, httpEntity, String.class);

        ResponseUtils.checkError(response, mapper);
    }

    private HttpHeaders getJsonHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return header;
    }

}
