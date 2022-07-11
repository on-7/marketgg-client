package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.domain.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.domain.dto.request.ProductModifyRequest;
import com.nhnacademy.marketgg.client.domain.dto.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    void createProduct(MultipartFile image, ProductCreateRequest productRequest) throws IOException;

    List<ProductResponse> retrieveProducts();

    ProductResponse retrieveProductDetails(Long productNo);

    void updateProduct(Long productId, MultipartFile image, ProductModifyRequest productRequest) throws IOException;

}
