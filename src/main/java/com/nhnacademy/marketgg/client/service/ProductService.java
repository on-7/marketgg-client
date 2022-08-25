package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.PageResult;
import com.nhnacademy.marketgg.client.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.SearchRequestForCategory;
import com.nhnacademy.marketgg.client.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.dto.response.SearchProductResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * 상품 서비스 입니다.
 *
 * @author 조현진
 * @version 1.0.0
 */
public interface ProductService {

    /**
     * 상품을 생성합니다.
     *
     * @param image          - MultipartFile 타입 입니다.
     * @param productRequest - 상품 생성을 위한 DTO 입니다.
     * @throws IOException - IOException을 발생시킵니다.
     * @author 조현진, 민아영
     * @since 1.0.0
     */
    void createProduct(final MultipartFile image, final ProductCreateRequest productRequest)
        throws IOException;

    /**
     * 모든 상품을 조회합니다.
     *
     * @return 모든 상품 List를 반환합니다.
     * @since 1.0.0
     */
    PageResult<ProductResponse> retrieveProducts();

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
     * @param categoryId       - 카테고리 소분류입니다. ex) 101 - 채소
     * @return - 해당 카테고리에 해당하는 모든 상품 리스트를 반환합니다.
     * @since 1.0.0
     */
    List<ProductResponse> retrieveProductsByCategory(final String categorizationCode,
                                                     final String categoryId);

    /**
     * productId에 해당하는 상품을 수정합니다.
     *
     * @param id             - 상품의 PK 입니다.
     * @param image          - MultiparttFile 타입 입니다.
     * @param productRequest - 상품 수정을 위한 DTO 입니다.
     * @throws IOException - IOException을 발생시킵니다.
     * @since 1.0.0
     */
    void updateProduct(final Long id, final MultipartFile image, final ProductUpdateRequest productRequest)
        throws IOException;

    /**
     * productId에 해당하는 상품을 삭제합니다.
     * 소프트 삭제입니다.
     *
     * @param id - 상품의 PK 입니다.
     * @since 1.0.0
     */
    void deleteProduct(final Long id);

    /**
     * 지정한 카테고리 번호 내에서 검색한 상품 목록을 반환합니다.
     *
     * @param searchRequest - 검색을 진행 할 정보입니다.
     * @return 선택한 카테고리 번호내에서 검색한 상품 목록을 반환합니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    List<SearchProductResponse> searchProductListByCategory(final SearchRequestForCategory searchRequest)
            throws JsonProcessingException;

    /**
     * 지정한 카테고리 번호 내에서 선택한 옵션으로 가격이 정렬된 상품 목록을 반환합니다.
     *
     * @param searchRequest - 검색을 진행 할 정보입니다.
     * @param option     - 지정한 검색 옵션입니다.
     * @return 선택한 카테고리 번호내에서 선택한 정렬옵션으로 가격이 정렬된 상품 목록을 반환합니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    List<SearchProductResponse> searchProductListByPrice(final SearchRequestForCategory searchRequest, final String option)
            throws JsonProcessingException;

}
