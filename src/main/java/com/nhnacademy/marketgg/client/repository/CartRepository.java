package com.nhnacademy.marketgg.client.repository;

import com.nhnacademy.marketgg.client.dto.request.ProductToCartRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 * 장바구니 관련 데이터를 처리합니다.
 *
 * @author 윤동열
 */
public interface CartRepository {

    /**
     * 사용자가 상품을 장바구니에 담을 때 실행됩니다.
     *
     * @param productAddRequest - 장바구니에 담는 상품의 정보입니다.
     */
    ResponseEntity<String> addToCart(final ProductToCartRequest productAddRequest);

    /**
     * 사용자가 장바구니를 조회할 때 실행됩니다.
     *
     * @return - 장바구니에 담긴 상품 목록입니다.
     */
    ResponseEntity<String> retrieveCart();

    /**
     * 장바구니에 담긴 상품의 수량을 변경할 때 실행됩니다.
     *
     * @param productUpdateRequest - 장바구니에 담긴 상품 중 수량을 변경하려는 상품의 정보입니다.
     */
    ResponseEntity<String> updateAmount(final ProductToCartRequest productUpdateRequest);

    /**
     * 장바구니에 담긴 상품을 삭제할 때 실행됩니다.
     *
     * @param products - 장바구니에 담긴 상품 중 삭제하려는 상품의 정보입니다.
     */
    ResponseEntity<String> delete(final List<Long> products);

}
