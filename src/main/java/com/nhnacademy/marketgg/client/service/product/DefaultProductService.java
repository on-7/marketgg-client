package com.nhnacademy.marketgg.client.service.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.product.ProductCreateRequest;
import com.nhnacademy.marketgg.client.dto.product.ProductUpdateRequest;
import com.nhnacademy.marketgg.client.dto.search.SearchRequestForCategory;
import com.nhnacademy.marketgg.client.dto.product.ProductResponse;
import com.nhnacademy.marketgg.client.dto.product.ProductListResponse;
import com.nhnacademy.marketgg.client.repository.product.ProductRepository;
import com.nhnacademy.marketgg.client.service.product.ProductService;
import java.io.IOException;
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
    public PageResult<ProductListResponse> retrieveProducts(int page) {
        return productRepository.retrieveProducts(page);
    }

    @Override
    public ProductResponse retrieveProductDetails(final Long id) {
        return this.productRepository.retrieveProductDetails(id);
    }

    @Override
    public PageResult<ProductListResponse> retrieveProductsByCategory(final String categoryId, final int page) {

        return this.productRepository.retrieveProductsByCategory(categoryId, page);
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
    public PageResult<ProductListResponse> searchProductListByCategory(final SearchRequestForCategory searchRequest)
            throws JsonProcessingException {

        return productRepository.searchProductListByCategory(searchRequest);
    }

    @Override
    public PageResult<ProductListResponse> searchProductListByPrice(final SearchRequestForCategory searchRequest, final String option)
            throws JsonProcessingException {

        return productRepository.searchProductListByPrice(searchRequest, option);
    }

}
