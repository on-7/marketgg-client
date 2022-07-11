package com.nhnacademy.marketgg.client.adapter;

import com.nhnacademy.marketgg.client.domain.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.domain.dto.response.ProductResponse;
import java.util.List;

public interface ProductAdapter {

    void createProduct(ProductCreateRequest productRequest);

    List<ProductResponse> retrieveProducts();

    ProductResponse retrieveProductDetails(Long productNo);

}
