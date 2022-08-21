package com.nhnacademy.marketgg.client.repository.cart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.client.dto.response.CartProductResponse;
import com.nhnacademy.marketgg.client.dto.response.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.response.common.ResponseUtils;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 장바구니 데이터를 처리하는 구현체입니다.
 * <p>
 * {@link CartRepository}
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
    public void addToCart(final ProductToCartRequest productAddRequest)
        throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<ProductToCartRequest> httpEntity = new HttpEntity<>(productAddRequest, getJsonHeader());

        ResponseEntity<CommonResult<String>> response =
            restTemplate.exchange(gatewayIp + CART, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
            });

        ResponseUtils.checkError(response);

    }

    @Override
    public List<CartProductResponse> retrieveCart() throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<Void> httpEntity = new HttpEntity<>(getJsonHeader());
        ResponseEntity<CommonResult<List<CartProductResponse>>> response =
            restTemplate.exchange(gatewayIp + CART, HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<>() {
                });

        ResponseUtils.checkError(response);

        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public void updateAmount(final ProductToCartRequest productUpdateRequest)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        HttpEntity<ProductToCartRequest> httpEntity = new HttpEntity<>(productUpdateRequest, getJsonHeader());
        ResponseEntity<CommonResult<String>> response =
            restTemplate.exchange(gatewayIp + CART, HttpMethod.PATCH, httpEntity,
                new ParameterizedTypeReference<>() {
                });

        ResponseUtils.checkError(response);
    }

    @Override
    public void delete(final List<Long> products)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        HttpEntity<List<Long>> httpEntity = new HttpEntity<>(products, getJsonHeader());
        ResponseEntity<CommonResult<String>> response =
            restTemplate.exchange(gatewayIp + CART, HttpMethod.DELETE, httpEntity, new ParameterizedTypeReference<>() {
            });

        ResponseUtils.checkError(response);
    }

    private HttpHeaders getJsonHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return header;
    }

}
