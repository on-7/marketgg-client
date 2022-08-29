package com.nhnacademy.marketgg.client.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import com.nhnacademy.marketgg.client.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.client.dto.response.CartProductResponse;
import com.nhnacademy.marketgg.client.repository.cart.CartRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class DefaultCartServiceTest {

    @InjectMocks
    DefaultCartService cartService;

    @Mock
    CartRepository cartRepository;

    static ProductToCartRequest productToCartRequest;

    @BeforeAll
    static void beforeAll() {
        productToCartRequest = new ProductToCartRequest(1L, 10);
        ReflectionTestUtils.setField(productToCartRequest, "id", 1L);
        ReflectionTestUtils.setField(productToCartRequest, "amount", 10);
    }

    @Test
    @DisplayName("장바구니에 상품 추가")
    void addProduct() throws Exception {
        willDoNothing().given(cartRepository).addToCart(productToCartRequest);

        cartService.addProduct(productToCartRequest);

        then(cartRepository).should(times(1)).addToCart(productToCartRequest);
    }

    @Test
    @DisplayName("장바구니 조회")
    void retrieveCarts() throws Exception {
        given(cartRepository.retrieveCart()).willReturn(new ArrayList<>());

        List<CartProductResponse> carts = cartService.retrieveCarts();

        then(cartRepository).should(times(1)).retrieveCart();

        assertThat(carts).isNotNull();
    }

    @Test
    @DisplayName("장바구니 상품 수량 변경")
    void updateAmount() throws Exception {
        willDoNothing().given(cartRepository).updateAmount(productToCartRequest);

        cartService.updateAmount(productToCartRequest);

        then(cartRepository).should(times(1)).updateAmount(productToCartRequest);
    }

    @Test
    @DisplayName("장바구니 상품 삭제")
    void deleteProducts() throws Exception {
        willDoNothing().given(cartRepository).delete(anyList());

        cartService.deleteProducts(new ArrayList<>());

        then(cartRepository).should(times(1)).delete(anyList());
    }

}
