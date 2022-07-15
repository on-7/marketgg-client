package com.nhnacademy.marketgg.client.repository;

import com.nhnacademy.marketgg.client.domain.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.domain.dto.request.ProductModifyRequest;
import com.nhnacademy.marketgg.client.domain.dto.response.ProductResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * 상품 레포지토리입니다.
 *
 * @version 1.0.0
 */
public interface ProductRepository {

    /**
     * 상품을 생성합니다.
     *
     * @param image          - MultipartFile 타입입니다.
     * @param productRequest - 상품 생성을 위한 DTO 입니다.
     * @throws IOException
     * @since 1.0.0
     */
    void createProduct(MultipartFile image, ProductCreateRequest productRequest) throws IOException;

    /**
     * 모든 상품을 조회합니다.
     *
     * @return - 상품 리스트를 반환합니다.
     * @since 1.0.0
     */
    List<ProductResponse> retrieveProducts();

    /**
     * 상품 상세 정보를 조회합니다.
     *
     * @param productId - 상품의 PK입니다.
     * @return - 상품을 반환합니다.
     * @since 1.0.0
     */
    ProductResponse retrieveProductDetails(Long productId);

    /**
     * 카테고리로 상품 목록을 조회합니다.
     *
     * @param categorizationCode - 카테고리 대분류 입니다. ex) 100 - 상품
     * @param categoryCode       - 카테고리 소분류 입니다. ex) 101 - 채소
     * @return - 상품 리스트를 반환합니다.
     * @since 1.0.0
     */
    List<ProductResponse> retrieveProductsByCategory(String categorizationCode,
                                                     String categoryCode);

    /**
     * 상품 번호로 상품을 찾은 뒤, 해당 상품을 수정합니다.
     *
     * @param productId      - 상품의 PK입니다.
     * @param image          - MultipartFile 타입입니다.
     * @param productRequest - 상품 수정을 위한 DTO 입니다.
     * @throws IOException
     * @since 1.0.0
     */
    void updateProduct(Long productId, MultipartFile image, ProductModifyRequest productRequest)
        throws IOException;

    /**
     * 상품 번호로 상품을 찾은 뒤, 소프트 삭제 합니다.
     *
     * @param productId - 상품의 PK 입니다.
     * @since 1.0.0
     */
    void deleteProduct(Long productId);

}
