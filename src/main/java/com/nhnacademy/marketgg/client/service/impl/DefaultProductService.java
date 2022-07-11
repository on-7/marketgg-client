package com.nhnacademy.marketgg.client.service.impl;

import com.nhnacademy.marketgg.client.adapter.ProductAdapter;
import com.nhnacademy.marketgg.client.domain.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.domain.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductAdapter productAdapter;

    public void createProduct(final ProductCreateRequest productRequest) {
        productAdapter.createProduct(productRequest);
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
