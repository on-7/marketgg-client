package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.domain.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.domain.dto.request.ProductModifyRequest;
import com.nhnacademy.marketgg.client.domain.dto.response.ProductResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * 상품 서비스 입니다.
 *
 * @version 1.0.0
 */
public interface ProductService {

    /**
     * 상품을 생성합니다.
     *
     * @param image          - MultipartFile 타입 입니다.
     * @param productRequest - 상품 생성을 위한 DTO 입니다.
     * @throws IOException
     * @since 1.0.0
     */
    void createProduct(final MultipartFile image, final ProductCreateRequest productRequest) throws IOException;

    /**
     * 모든 상품을 조회합니다.
     *
     * @return 모든 상품 List를 반환합니다.
     * @since 1.0.0
     */
    List<ProductResponse> retrieveProducts();

    /**
     * 상품 상세 정보를 조회합니다.
     *
     * @param id - 상품의 PK 입니다.
     * @return 파라미터로 받은 PK에 해당하는 상품의 상세 정보를 반환합니다.
     * @since 1.0.0
     */
    ProductResponse retrieveProductDetails(final Long id);

    /**
     * 카테고리로 조회한 모든 상품을 반환 합니다.
     *
     * @param categorizationCode - 카테고리 대분류입니다. ex) 100 - 상품
     * @param categoryCode       - 카테고리 소분류입니다. ex) 101 - 채소
     * @return - 해당 카테고리에 해당하는 모든 상품 리스트를 반환합니다.
     * @since 1.0.0
     */
    List<ProductResponse> retrieveProductsByCategory(final String categorizationCode,
                                                     final String categoryCode);

    /**
     * productId에 해당하는 상품을 수정합니다.
     *
     * @param id      - 상품의 PK 입니다.
     * @param image          - MultiparttFile 타입 입니다.
     * @param productRequest - 상품 수정을 위한 DTO 입니다.
     * @throws IOException
     * @since 1.0.0
     */
    void updateProduct(final Long id, final MultipartFile image, final ProductModifyRequest productRequest)
        throws IOException;

    /**
     * productId에 해당하는 상품을 삭제합니다.
     * 소프트 삭제입니다.
     *
     * @param id - 상품의 PK 입니다.
     * @since 1.0.0
     */
    void deleteProduct(final Long id);

}
