package com.nhnacademy.marketgg.client.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.nhnacademy.marketgg.client.repository.ProductRepository;
import com.nhnacademy.marketgg.client.service.impl.DefaultProductService;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private DefaultProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("카테고리 번호내 목록에서 상품 검색")
    void testSearchProductListByCategory() {
        given(productRepository.searchProductListByCategory(anyString(), anyString(), anyInt())).willReturn(List.of());

        productService.searchProductListByCategory("100", "hi", 1);

        then(productRepository).should(times(1)).searchProductListByCategory(anyString(), anyString(), anyInt());
    }

    @Test
    @DisplayName("카테고리 번호 내 목록 및 가격 옵션 검색")
    void testSearchProductListByPrice() {
        given(productRepository.searchProductListByPrice(anyString(), anyString(), anyString(), anyInt())).willReturn(
                List.of());

        productService.searchProductListByPrice("100", "desc", "hi", 1);

        then(productRepository).should(times(1))
                               .searchProductListByPrice(anyString(), anyString(), anyString(), anyInt());
    }

}
