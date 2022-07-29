package com.nhnacademy.marketgg.client.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.client.dto.response.CartProductResponse;
import com.nhnacademy.marketgg.client.dto.response.common.ErrorEntity;
import com.nhnacademy.marketgg.client.dto.response.common.ListResponse;
import com.nhnacademy.marketgg.client.exception.ClientException;
import com.nhnacademy.marketgg.client.exception.NotFoundException;
import com.nhnacademy.marketgg.client.repository.CartRepository;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 장바구니 데이터를 처리하는 구현체입니다.
 * <p>
 * {@link com.nhnacademy.marketgg.client.repository.CartRepository}
 */
@Component
@RequiredArgsConstructor
public class CartAdapter implements CartRepository {

    private static final String CART = "/cart";

    @Value("${gg.gateway-origin}")
    private String requestUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    public void addToCart(final ProductToCartRequest productAddRequest) throws JsonProcessingException {
        HttpEntity<ProductToCartRequest> httpEntity = new HttpEntity<>(productAddRequest, getJsonHeader());

        ResponseEntity<String> response =
            restTemplate.postForEntity(requestUrl + CART, httpEntity, String.class);

        checkError(response);

    }

    public List<CartProductResponse> retrieveCart() throws JsonProcessingException {
        HttpEntity<Void> httpEntity = new HttpEntity<>(getJsonHeader());
        ResponseEntity<String> response =
            restTemplate.exchange(requestUrl + CART, HttpMethod.GET, httpEntity, String.class);

        checkError(response);


        ListResponse<CartProductResponse> cartProductResponseListResponse =
            mapper.readValue(response.getBody(), new TypeReference<>() {
            });

        return cartProductResponseListResponse.getData();
    }

    // public void update

    private void checkError(ResponseEntity<String> response) throws JsonProcessingException {
        ErrorEntity errorEntity = mapper.readValue(response.getBody(), ErrorEntity.class);

        if (Objects.equals(response.getStatusCode(), HttpStatus.NOT_FOUND)) {
            throw new NotFoundException(errorEntity.getMessage());
        } else if (response.getStatusCode().is4xxClientError()) {
            throw new ClientException(errorEntity.getMessage());
        }
    }

    private HttpHeaders getJsonHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return header;
    }

}
