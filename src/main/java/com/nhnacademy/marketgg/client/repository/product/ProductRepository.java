package com.nhnacademy.marketgg.client.repository.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.product.ProductCreateRequest;
import com.nhnacademy.marketgg.client.dto.product.ProductListResponse;
import com.nhnacademy.marketgg.client.dto.product.ProductResponse;
import com.nhnacademy.marketgg.client.dto.product.ProductUpdateRequest;
import com.nhnacademy.marketgg.client.dto.search.SearchRequestForCategory;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 상품 레포지토리입니다.
 *
 * @author 박세완
 * @author 조현진
 * @version 1.0.0
 */
public interface ProductRepository {

    /**
     * 상품을 생성합니다.
     *
     * @param image          - MultipartFile 타입입니다.
     * @param productRequest - 상품 생성을 위한 DTO 입니다.
     * @throws IOException 입출력에 관한 예외처리입니다.
     * @since 1.0.0
     */
    void createProduct(final MultipartFile image, final ProductCreateRequest productRequest)
            throws IOException;

    /**
     * 모든 상품을 조회합니다.
     *
     * @return - 상품 리스트를 반환합니다.
     * @since 1.0.0
     */
    PageResult<ProductListResponse> retrieveProducts(int page);

    /**
     * 상품 상세 정보를 조회합니다.
     *
     * @param productId - 상품의 PK입니다.
     * @return - 상품을 반환합니다.
     * @since 1.0.0
     */
    ProductResponse retrieveProductDetails(final Long productId);

    /**
     * 카테고리로 상품 목록을 조회합니다.
     *
     * @param categoryId - 카테고리 소분류 입니다. ex) 101 - 채소
     * @return - 상품 리스트를 반환합니다.
     * @since 1.0.0
     */
    PageResult<ProductListResponse> retrieveProductsByCategory(final String categoryId, final int page);

    /**
     * 상품 번호로 상품을 찾은 뒤, 해당 상품을 수정합니다.
     *
     * @param productId      - 상품의 PK입니다.
     * @param image          - MultipartFile 타입입니다.
     * @param productRequest - 상품 수정을 위한 DTO 입니다.
     * @throws IOException 입출력에 관한 예외처리입니다.
     * @since 1.0.0
     */
    void updateProduct(final Long productId, final MultipartFile image,
                       final ProductUpdateRequest productRequest)
            throws IOException;

    /**
     * 상품 번호로 상품을 찾은 뒤, 소프트 삭제 합니다.
     *
     * @param productId - 상품의 PK 입니다.
     * @since 1.0.0
     */
    void deleteProduct(final Long productId);

    /**
     * 지정한 카테고리 번호 내에서 검색한 상품 목록을 반환합니다.
     *
     * @param searchRequest - 검색을 진행 할 정보입니다.
     * @return 선택한 카테고리 번호내에서 검색한 상품 목록을 반환합니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    PageResult<ProductListResponse> searchProductListByCategory(final SearchRequestForCategory searchRequest)
            throws JsonProcessingException;

    /**
     * 지정한 카테고리 번호 내에서 선택한 옵션으로 가격이 정렬된 상품 목록을 반환합니다.
     *
     * @param searchRequest - 검색을 진행 할 정보입니다.
     * @param option        - 지정한 검색 옵션입니다.
     * @return 선택한 카테고리 번호내에서 선택한 정렬옵션으로 가격이 정렬된 상품 목록을 반환합니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    PageResult<ProductListResponse> searchProductListByPrice(final SearchRequestForCategory searchRequest,
                                                             final String option) throws JsonProcessingException;

}
