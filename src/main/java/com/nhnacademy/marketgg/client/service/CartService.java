package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.client.dto.response.CartProductResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import java.util.List;

/**
 * 장바구니 관련 비즈니스 로직을 처리합니다.
 *
 * @version 1.0.0
 */
public interface CartService {

    /**
     * 회원이 상품을 장바구니에 담을 때 실행됩니다.
     *
     * @param productAddRequest - 장바구니에 담으려는 상품 정보
     */
    void addProduct(final ProductToCartRequest productAddRequest)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    /**
     * 회원이 장바구니에 담은 상품을 조회합니다.
     *
     * @return - 장바구니 목록
     */
    List<CartProductResponse> retrieveCarts()
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    /**
     * 장바구니에 담긴 상품 수량을 변경합니다.
     *
     * @param productUpdateRequest - 수량을 변경하려는 상품 정보
     */
    void updateAmount(final ProductToCartRequest productUpdateRequest)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    /**
     * 장바구니에 담긴 상품을 삭제합니다.
     *
     * @param products - 삭제하려는 상품 목록
     */
    void deleteProducts(final List<Long> products)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

}
