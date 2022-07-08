package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.adapter.ProductAdapter;
import com.nhnacademy.marketgg.client.domain.dto.request.ProductCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultProductService {

    private final ProductAdapter productAdapter;

    public void createProduct(ProductCreateRequest productRequest) {
        productAdapter.createProject(productRequest);
    }
}
