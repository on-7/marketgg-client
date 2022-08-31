package com.nhnacademy.marketgg.client.service.cart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.cart.CartProductResponse;
import com.nhnacademy.marketgg.client.dto.product.ProductToCartRequest;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.cart.CartRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 장바구니 관련 비즈니스 로직을 처리하는 구현체입니다.
 * <p>
 * {@link CartService}
 *
 * @author 윤동열
 */
@Service
@RequiredArgsConstructor
public class DefaultCartService implements CartService {

    private final CartRepository cartRepository;

    @Override
    public void addProduct(final ProductToCartRequest productAddRequest)
            throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        cartRepository.addToCart(productAddRequest);
    }

    @Override
    public List<CartProductResponse> retrieveCarts()
            throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        return cartRepository.retrieveCart();
    }

    @Override
    public void updateAmount(final ProductToCartRequest productUpdateRequest)
            throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        cartRepository.updateAmount(productUpdateRequest);
    }

    @Override
    public void deleteProducts(final List<Long> products)
            throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        cartRepository.delete(products);
    }

}
