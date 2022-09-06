package com.nhnacademy.marketgg.client.service.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.product.ProductCreateRequest;
import com.nhnacademy.marketgg.client.dto.product.ProductListResponse;
import com.nhnacademy.marketgg.client.dto.product.ProductResponse;
import com.nhnacademy.marketgg.client.dto.product.ProductUpdateRequest;
import com.nhnacademy.marketgg.client.dto.search.SearchRequestForCategory;
import com.nhnacademy.marketgg.client.repository.product.ProductRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    public void createProduct(final MultipartFile image, final ProductCreateRequest productRequest)
            throws IOException {

        this.productRepository.createProduct(image, productRequest);
    }

    @Override
    @Cacheable(value = "product", key = "#page")
    public PageResult<ProductListResponse> retrieveProducts(int page) {
        log.info("상품 정보가 캐싱되었습니다.페이지: {}", page);
        return productRepository.retrieveProducts(page);
    }

    @Override
    public ProductResponse retrieveProductDetails(final Long id) {
        return this.productRepository.retrieveProductDetails(id);
    }

    @Override
    @Cacheable(value = "product", key = "#page")
    public PageResult<ProductListResponse> retrieveProductsByCategory(final String categoryId, final int page) {
        log.info("카테고리로 상품 조회 정보가 캐싱되었습니다. 카테고리 번호: {}", categoryId);
        return this.productRepository.retrieveProductsByCategory(categoryId, page);
    }

    @Override
    @CacheEvict(value = "product", key = "#id")
    public void updateProduct(final Long id, final MultipartFile image,
                              final ProductUpdateRequest productRequest) throws IOException {
        log.info("상품 정보가 수정되어 캐시가 삭제됩니다. 상품번호: {}", id);
        this.productRepository.updateProduct(id, image, productRequest);
    }

    @Override
    public void deleteProduct(final Long id) {
        this.productRepository.deleteProduct(id);
    }

    @Override
    public PageResult<ProductListResponse> searchProductListByCategory(final SearchRequestForCategory searchRequest)
            throws JsonProcessingException {

        return productRepository.searchProductListByCategory(searchRequest);
    }

    @Override
    public PageResult<ProductListResponse> searchProductListByPrice(final SearchRequestForCategory searchRequest,
                                                                    final String option)
            throws JsonProcessingException {

        return productRepository.searchProductListByPrice(searchRequest, option);
    }

}
