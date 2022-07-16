package com.nhnacademy.marketgg.client.repository;

import com.nhnacademy.marketgg.client.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductModifyRequest;
import com.nhnacademy.marketgg.client.dto.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductRepository {

    void createProduct(MultipartFile image, ProductCreateRequest productRequest) throws IOException;

    List<ProductResponse> retrieveProducts();

    //TODO: 그냥 Id로 바꾸기.
    ProductResponse retrieveProductDetails(Long productId);

    List<ProductResponse> retrieveProductsByCategory(String categorizationCode, String categoryCode);

    void updateProduct(Long productId, MultipartFile image, ProductModifyRequest productRequest) throws IOException;

    void deleteProduct(Long productId);

}
