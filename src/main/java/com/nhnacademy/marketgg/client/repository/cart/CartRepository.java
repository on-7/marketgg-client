package com.nhnacademy.marketgg.client.repository.cart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.product.ProductToCartRequest;
import com.nhnacademy.marketgg.client.dto.cart.CartProductResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import java.util.List;

/**
 * 장바구니 관련 데이터를 처리합니다.
 */
public interface CartRepository {

    /**
     * 사용자가 상품을 장바구니에 담을 때 실행됩니다.
     *
     * @param productAddRequest - 장바구니에 담는 상품의 정보입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     */
    void addToCart(final ProductToCartRequest productAddRequest)
        throws JsonProcessingException, UnAuthenticException, UnAuthorizationException;

    /**
     * 사용자가 장바구니를 조회할 때 실행됩니다.
     *
     * @return - 장바구니에 담긴 상품 목록입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     */
    List<CartProductResponse> retrieveCart()
        throws JsonProcessingException, UnAuthenticException, UnAuthorizationException;

    /**
     * 장바구니에 담긴 상품의 수량을 변경할 때 실행됩니다.
     *
     * @param productUpdateRequest - 장바구니에 담긴 상품 중 수량을 변경하려는 상품의 정보입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     */
    void updateAmount(final ProductToCartRequest productUpdateRequest)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

    /**
     * 장바구니에 담긴 상품을 삭제할 때 실행됩니다.
     *
     * @param products - 장바구니에 담긴 상품 중 삭제하려는 상품의 정보입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     */
    void delete(final List<Long> products)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException;

}
