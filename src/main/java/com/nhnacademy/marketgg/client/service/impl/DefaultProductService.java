package com.nhnacademy.marketgg.client.service.impl;

import com.nhnacademy.marketgg.client.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductModifyRequest;
import com.nhnacademy.marketgg.client.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.dto.response.SearchProductResponse;
import com.nhnacademy.marketgg.client.repository.ProductRepository;
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
    public List<ProductResponse> retrieveProducts() {
        List<ProductResponse> productResponses = productRepository.retrieveProducts();
        return productResponses;
    }

    @Override
    public ProductResponse retrieveProductDetails(final Long id) {
        return this.productRepository.retrieveProductDetails(id);
    }

    @Override
    public List<ProductResponse> retrieveProductsByCategory(final String categorizationCode,
                                                            final String categoryCode) {

        return this.productRepository.retrieveProductsByCategory(categorizationCode, categoryCode);
    }

    @Override
    public void updateProduct(final Long id, final MultipartFile image,
                              final ProductModifyRequest productRequest) throws IOException {

        this.productRepository.updateProduct(id, image, productRequest);
    }

    @Override
    public void deleteProduct(final Long id) {
        this.productRepository.deleteProduct(id);
    }

    @Override
    public List<SearchProductResponse> searchProductList(final String keyword, final String page) {
        return productRepository.searchProductList(keyword, page);
    }

    @Override
    public List<SearchProductResponse> searchProductListByCategory(final String categoryId, final String keyword, final String page) {
        return productRepository.searchProductListByCategory(categoryId, keyword, page);
    }

    @Override
    public List<SearchProductResponse> searchProductListByPrice(final String categoryId, final String option, final String keyword, final String page) {
        return productRepository.searchProductListByPrice(categoryId, option, keyword, page);
    }

}
