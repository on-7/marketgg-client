package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.client.dto.response.CartProductResponse;
import com.nhnacademy.marketgg.client.dto.response.common.ResponseUtils;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.CartRepository;
import com.nhnacademy.marketgg.client.service.CartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 장바구니 관련 비즈니스 로직을 처리하는 구현체입니다.
 * <p>
 * {@link com.nhnacademy.marketgg.client.service.CartService}
 *
 * @author 윤동열
 */
@Service
@RequiredArgsConstructor
public class DefaultCartService implements CartService {

    private final ObjectMapper mapper;
    private final CartRepository cartRepository;

    @Override
    public void addProduct(final ProductToCartRequest productAddRequest)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        ResponseEntity<String> response = cartRepository.addToCart(productAddRequest);

        ResponseUtils.checkError(response, mapper);
    }

    @Override
    public List<CartProductResponse> retrieveCarts()
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        ResponseEntity<String> response = cartRepository.retrieveCart();
        ResponseUtils.checkError(response, mapper);

        return ResponseUtils.getListData(response.getBody(), mapper);
    }

    @Override
    public void updateAmount(final ProductToCartRequest productUpdateRequest)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        ResponseEntity<String> response = cartRepository.updateAmount(productUpdateRequest);

        ResponseUtils.checkError(response, mapper);
    }

    @Override
    public void deleteProducts(final List<Long> products)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        ResponseEntity<String> response = cartRepository.delete(products);

        ResponseUtils.checkError(response, mapper);
    }

}
