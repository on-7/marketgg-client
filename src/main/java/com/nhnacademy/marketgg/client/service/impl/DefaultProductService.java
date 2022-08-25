package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.PageResult;
import com.nhnacademy.marketgg.client.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.SearchRequestForCategory;
import com.nhnacademy.marketgg.client.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.dto.response.SearchProductResponse;
import com.nhnacademy.marketgg.client.repository.product.ProductRepository;
import com.nhnacademy.marketgg.client.service.ProductService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    public void createProduct(final MultipartFile image, final ProductCreateRequest productRequest)
        throws IOException {

        this.productRepository.createProduct(image, productRequest);
    }

    @Override
    public PageResult<ProductResponse> retrieveProducts(int page) {
        return productRepository.retrieveProducts(page);
    }

    @Override
    public ProductResponse retrieveProductDetails(final Long id) {
        return this.productRepository.retrieveProductDetails(id);
    }

    @Override
    public List<ProductResponse> retrieveProductsByCategory(final String categorizationCode,
                                                            final String categoryId) {

        return this.productRepository.retrieveProductsByCategory(categorizationCode, categoryId);
    }

    @Override
    public void updateProduct(final Long id, final MultipartFile image,
                              final ProductUpdateRequest productRequest) throws IOException {

        this.productRepository.updateProduct(id, image, productRequest);
    }

    @Override
    public void deleteProduct(final Long id) {
        this.productRepository.deleteProduct(id);
    }

    @Override
    public List<SearchProductResponse> searchProductListByCategory(final SearchRequestForCategory searchRequest)
            throws JsonProcessingException {

        return productRepository.searchProductListByCategory(searchRequest);
    }

    @Override
    public List<SearchProductResponse> searchProductListByPrice(final SearchRequestForCategory searchRequest, final String option)
            throws JsonProcessingException {

        return productRepository.searchProductListByPrice(searchRequest, option);
    }

}
