package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.response.CategoryRetrieveResponse;

import java.util.List;

/**
 * 카테고리 서비스입니다.
 *
 * @version 1.0.0
 * @author 박세완, 김정민
 */
public interface CategoryService {

    /**
     * 입력받은 카테고리 정보로 Adapter 메소드를 실행합니다.
     *
     * @param categoryRequest - 카테고리 등록을 위한 입력정보를 담은 객체입니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    void createCategory(final CategoryCreateRequest categoryRequest) throws JsonProcessingException;

    /**
     * 지정한 카테고리를 반환하는 Adapter 메소드를 실행합니다.
     *
     * @param id - 반환 할 카테고리의 식별번호입니다.
     * @return 지정한 카테고리의 정보를 담은 객체를 반환합니다.
     * @since 1.0.0
     */
    CategoryRetrieveResponse retrieveCategory(final String id);

    /**
     * 카테고리 분류 전체를 반환하는 Adapter 메소드를 실행합니다.
     *
     * @return 카테고리 분류 전체를 담은 List 를 반환합니다.
     * @since 1.0.0
     */
    List<CategorizationRetrieveResponse> retrieveCategorizations();

    /**
     * 카테고리 전체를 반환하는 Adapter 메소드를 실행합니다.
     *
     * @return 카테고리 전체를 담은 List 를 반환합니다.
     * @since 1.0.0
     */
    List<CategoryRetrieveResponse> retrieveCategories();

    /**
     * 지정한 카테고리를 입력받은 정보로 수정하는 Adapter 메소드를 실행합니다.
     *
     * @param categoryId - 수정할 카테고리의 식별번호입니다.
     * @param categoryRequest - 카테고리 수정을 위한 입력정보를 담은 객체입니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    void updateCategory(final String categoryId, final CategoryUpdateRequest categoryRequest)
            throws JsonProcessingException;

    /**
     * 지정한 카테고리를 삭제하는 Adapter 메소드를 실행합니다.
     *
     * @param categoryId - 삭제할 카테고리의 식별번호입니다.
     * @since 1.0.0
     */
    void deleteCategory(final String categoryId);

}
