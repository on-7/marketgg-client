package com.nhnacademy.marketgg.client.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

import com.nhnacademy.marketgg.client.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.dummy.Dummy;
import com.nhnacademy.marketgg.client.repository.ProductRepository;
import com.nhnacademy.marketgg.client.service.impl.DefaultProductService;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private DefaultProductService productService;

    @Mock
    private ProductRepository productRepository;

    private ProductCreateRequest productCreateRequest;
    private ProductUpdateRequest productUpdateRequest;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        productCreateRequest = Dummy.getDummyProductCreateRequest();
        productUpdateRequest = Dummy.getDummyProductUpdateRequest();
        productResponse = Dummy.getDummyProductResponse();
    }

    @Test
    @DisplayName("상품 생성 테스트")
    void testCreateProduct() throws IOException {
        MockMultipartFile image = getImage();
        doNothing().when(productRepository)
                   .createProduct(any(MockMultipartFile.class), any(ProductCreateRequest.class));

        productService.createProduct(image, productCreateRequest);

        then(productRepository).should(times(1))
                               .createProduct(any(MockMultipartFile.class), any(ProductCreateRequest.class));
    }

    @Test
    @DisplayName("카테고리 번호내 목록에서 상품 검색")
    void testSearchProductListByCategory() {
        given(productRepository.searchProductListByCategory(anyString(), anyString(), anyInt())).willReturn(
            List.of());

        productService.searchProductListByCategory("100", "hi", 1);

        then(productRepository).should(times(1))
                               .searchProductListByCategory(anyString(), anyString(), anyInt());
    }

    @Test
    @DisplayName("카테고리 번호 내 목록 및 가격 옵션 검색")
    void testSearchProductListByPrice() {
        given(productRepository.searchProductListByPrice(anyString(),
                                                         anyString(),
                                                         anyString(),
                                                         anyInt())).willReturn(
            List.of());

        productService.searchProductListByPrice("100", "desc", "hi", 1);

        then(productRepository).should(times(1))
                               .searchProductListByPrice(anyString(), anyString(), anyString(), anyInt());
    }

    private MockMultipartFile getImage() throws IOException {
        URL url = getClass().getClassLoader().getResource("lee.png");
        String filePath = Objects.requireNonNull(url).getPath();

        return new MockMultipartFile("image", "test.png", "image/png", new FileInputStream(filePath));
    }

}
