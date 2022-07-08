package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.domain.dto.request.ProductCreateRequest;

public interface ProductService {

    void createProduct(ProductCreateRequest productRequest);
}
