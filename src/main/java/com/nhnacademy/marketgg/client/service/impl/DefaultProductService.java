package com.nhnacademy.marketgg.client.service.impl;

import com.nhnacademy.marketgg.client.domain.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.domain.dto.request.ProductModifyRequest;
import com.nhnacademy.marketgg.client.domain.dto.response.ProductResponse;
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

        productRepository.createProduct(image, productRequest);
    }

    @Override
    public List<ProductResponse> retrieveProducts() {
        return productRepository.retrieveProducts();
    }

    @Override
    public ProductResponse retrieveProductDetails(Long productNo) {
        return productRepository.retrieveProductDetails(productNo);
    }

    @Override
    public List<ProductResponse> retrieveProductsByCategory(String categorizationCode, String categoryCode) {
        return productRepository.retrieveProductsByCategory(categorizationCode, categoryCode);
    }

    @Override
    public void updateProduct(Long productId, MultipartFile image, ProductModifyRequest productRequest)
        throws IOException {

        productRepository.updateProduct(productId, image, productRequest);
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteProduct(productId);
    }

}
