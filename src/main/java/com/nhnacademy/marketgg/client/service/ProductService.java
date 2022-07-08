package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.domain.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.domain.dto.response.ProductResponse;
import java.util.List;

public interface ProductService {

    void createProduct(ProductCreateRequest productRequest);

    List<ProductResponse> retrieveProducts();
}
