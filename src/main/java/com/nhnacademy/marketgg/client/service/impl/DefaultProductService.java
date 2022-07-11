package com.nhnacademy.marketgg.client.service.impl;

import com.nhnacademy.marketgg.client.adapter.ProductAdapter;
import com.nhnacademy.marketgg.client.domain.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.domain.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductAdapter productAdapter;

    public void createProduct(final MultipartFile image,
                              final ProductCreateRequest productRequest) throws IOException {

        productAdapter.createProduct(image, productRequest);
    }

    @Override
    public List<ProductResponse> retrieveProducts() {
        return productAdapter.retrieveProducts();
    }

    @Override
    public ProductResponse retrieveProductDetails(Long productNo) {
        return productAdapter.retrieveProductDetails(productNo);
    }

}
