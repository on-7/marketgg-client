package com.nhnacademy.marketgg.client.repository;

import com.nhnacademy.marketgg.client.domain.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.domain.dto.request.ProductModifyRequest;
import com.nhnacademy.marketgg.client.domain.dto.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 상품 레포지토리입니다.
 *
 * @version 1.0.0
 */
public interface ProductRepository {

    /**
     * 상품을 생성합니다.
     *
     * @param image - MultipartFile 타입입니다.
     * @param productRequest - 상품 생성을 위한 DTO 입니다.
     * @throws IOException
     * @since 1.0.0
     */
    void createProduct(MultipartFile image, ProductCreateRequest productRequest) throws IOException;

    List<ProductResponse> retrieveProducts();

    ProductResponse retrieveProductDetails(Long productId);

    List<ProductResponse> retrieveProductsByCategory(String categorizationCode, String categoryCode);

    void updateProduct(Long productId, MultipartFile image, ProductModifyRequest productRequest) throws IOException;

    void deleteProduct(Long productId);

}
