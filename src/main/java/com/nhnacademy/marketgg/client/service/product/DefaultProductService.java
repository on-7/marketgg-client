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
import java.util.List;

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

    @Override
    @CacheEvict(value = "product", allEntries = true)
    public void createProduct(final MultipartFile image, final ProductCreateRequest productRequest)
            throws IOException {
        log.info("상품이 새로 등록되어 캐시를 삭제합니다.");
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
    @Cacheable(value = "product", key = "#categoryId")
    public PageResult<ProductListResponse> retrieveProductsByCategory(final String categoryId, final int page) {
        log.info("카테고리로 상품 조회 정보가 캐싱되었습니다. 카테고리 번호: {}", categoryId);
        return this.productRepository.retrieveProductsByCategory(categoryId, page);
    }

    @Override
    @CacheEvict(value = "product", key = "#id", allEntries = true)
    public void updateProduct(final Long id, final MultipartFile image,
                              final ProductUpdateRequest productRequest) throws IOException {
        log.info("상품 정보가 수정되어 캐시가 삭제됩니다. 상품번호: {}", id);
        this.productRepository.updateProduct(id, image, productRequest);
    }

    @Override
    @CacheEvict(value = "product", key = "#id", allEntries = true)
    public void deleteProduct(final Long id) {
        log.info("상품 정보가 수정되어 캐시가 삭제됩니다. 상품번호: {}", id);
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

    @Override
    @Cacheable(cacheNames = "productSuggestStore", key = "#searchRequest.keyword")
    public String[] suggestProductList(SearchRequestForCategory searchRequest) throws JsonProcessingException {
        PageResult<ProductListResponse> responses = productRepository.searchProductListByCategory(searchRequest);
        List<ProductListResponse> products = responses.getData();
        String[] productNameList = new String[5];

        for (int i = 0; i < products.size(); i++) {
            productNameList[i] = products.get(i).getProductName();
            if (i == 4) {
                break;
            }
        }
        return productNameList;
    }

}
