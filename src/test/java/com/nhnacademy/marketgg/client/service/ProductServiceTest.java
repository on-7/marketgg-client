package com.nhnacademy.marketgg.client.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.PageResult;
import com.nhnacademy.marketgg.client.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.SearchRequestForCategory;
import com.nhnacademy.marketgg.client.dto.response.ProductListResponse;
import com.nhnacademy.marketgg.client.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.dummy.Dummy;
import com.nhnacademy.marketgg.client.repository.product.ProductRepository;
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
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private DefaultProductService productService;

    @Mock
    private ProductRepository productRepository;

    private ProductCreateRequest productCreateRequest;
    private ProductUpdateRequest productUpdateRequest;
    private ProductResponse productResponse;

    private PageResult<ProductListResponse> pageResult;

    @BeforeEach
    void setUp() {
        productCreateRequest = Dummy.getDummyProductCreateRequest();
        productUpdateRequest = Dummy.getDummyProductUpdateRequest();
        productResponse = Dummy.getDummyProductResponse();

        pageResult = new PageResult<>();

        ReflectionTestUtils.setField(pageResult, "pageNumber", 0);
        ReflectionTestUtils.setField(pageResult, "pageSize", 10);
        ReflectionTestUtils.setField(pageResult, "totalPages", 0);
        ReflectionTestUtils.setField(pageResult, "data", null);
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
    @DisplayName("상품 전체조회 테스트")
    void testRetrieveProducts() {
        given(productRepository.retrieveProducts(anyInt())).willReturn(new PageResult<>());

        productService.retrieveProducts(1);

        then(productRepository).should(times(1)).retrieveProducts(anyInt());
    }

    @Test
    @DisplayName("상품 상세조회 테스트")
    void testRetrieveProductDetails() {
        given(productRepository.retrieveProductDetails(anyLong())).willReturn(productResponse);

        productService.retrieveProductDetails(1L);

        then(productRepository).should(times(1)).retrieveProductDetails(anyLong());
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void testUpdateProduct() throws IOException {
        MockMultipartFile image = getImage();
        willDoNothing().given(productRepository).updateProduct(anyLong(), any(MockMultipartFile.class), any(ProductUpdateRequest.class));

        productService.updateProduct(1L, image, productUpdateRequest);

        then(productRepository).should(times(1)).updateProduct(anyLong(), any(MockMultipartFile.class), any(ProductUpdateRequest.class));
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void testDeleteProduct() throws IOException {
        willDoNothing().given(productRepository).deleteProduct(anyLong());

        productService.deleteProduct(1L);

        then(productRepository).should(times(1)).deleteProduct(anyLong());
    }

    @Test
    @DisplayName("카테고리 번호내 목록에서 상품 검색")
    void testSearchProductListByCategory() throws JsonProcessingException {
        given(productRepository.searchProductListByCategory(any(SearchRequestForCategory.class))).willReturn(pageResult);

        productService.searchProductListByCategory(new SearchRequestForCategory("100", "hello", 0, 10));

        then(productRepository).should(times(1)).searchProductListByCategory(any(SearchRequestForCategory.class));
    }

    @Test
    @DisplayName("카테고리 번호 내 목록 및 가격 옵션 검색")
    void testSearchProductListByPrice() throws JsonProcessingException {
        given(productRepository.searchProductListByPrice(any(SearchRequestForCategory.class), anyString())).willReturn(
            pageResult);

        productService.searchProductListByPrice(new SearchRequestForCategory("100", "hello", 0, 10), "asc");

        then(productRepository).should(times(1))
                               .searchProductListByPrice(any(SearchRequestForCategory.class), anyString());
    }

    private MockMultipartFile getImage() throws IOException {
        URL url = getClass().getClassLoader().getResource("lee.png");
        String filePath = Objects.requireNonNull(url).getPath();

        return new MockMultipartFile("image", "test.png", "image/png", new FileInputStream(filePath));
    }

}
