package com.nhnacademy.marketgg.client.repository.category;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.category.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.category.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.category.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.category.CategoryUpdateRequest;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import java.util.List;

/**
 * 카테고리 Repository 입니다.
 *
 * @author 박세완, 김정민
 * @version 1.0.0
 */
public interface CategoryRepository {

    /**
     * 입력받은 정보로 카테고리 등록을 수행하는 메소드입니다.
     *
     * @param categoryRequest - 카테고리 등록을 위한 입력정보를 담은 객체입니다.
     * @throws JsonProcessingException  Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    void createCategory(final CategoryCreateRequest categoryRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException;

    /**
     * 지정한 카테고리를 반환하는 메소드입니다.
     *
     * @param id - 반환할 카테고리의 식별번호입니다.
     * @return 반환할 카테고리의 정보를 담은 객체입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    CategoryRetrieveResponse retrieveCategory(final String id) throws UnAuthenticException, UnAuthorizationException;

    /**
     * 카테고리 전체 목록을 반환하는 메소드입니다.
     *
     * @return 전체 카테고리를 담은 List 를 반환합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    List<CategoryRetrieveResponse> retrieveCategories() throws UnAuthenticException, UnAuthorizationException;

    /**
     * 상품에 해당하는 카테고리 목록을 반환합니다.
     */
    List<CategoryRetrieveResponse> retrieveCategoriesOnlyProducts();

    /**
     * 카테고리 분류 전체 목록을 반환하는 메소드입니다.
     *
     * @return 전체 카테고리 분류를 담은 List 를 반환합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    List<CategorizationRetrieveResponse> retrieveCategorizations()
            throws UnAuthenticException, UnAuthorizationException;

    /**
     * 입력받은 정보로 지정한 카테고리를 수정하는 메소드입니다.
     *
     * @param id              - 수정할 카테고리의 식별번호입니다.
     * @param categoryRequest - 카테고리 수정을 위한 입력정보를 담은 객체입니다.
     * @throws JsonProcessingException  Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    void updateCategory(final String id, final CategoryUpdateRequest categoryRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException;

    /**
     * 지정한 카테고리를 삭제하는 메소드입니다.
     *
     * @param id - 삭제할 카테고리의 식별번호입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    void deleteCategory(final String id) throws UnAuthenticException, UnAuthorizationException;

}
