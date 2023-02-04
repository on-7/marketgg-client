package com.nhnacademy.marketgg.client.service.category;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.category.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.category.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.category.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.category.CategoryUpdateRequest;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import java.util.List;

/**
 * 카테고리 서비스입니다.
 *
 * @author 박세완, 김정민, 조현진
 * @version 1.0.0
 */
public interface CategoryService {

    /**
     * 입력받은 카테고리 정보로 Adapter 메소드를 실행합니다.
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
     * 지정한 카테고리를 반환하는 Adapter 메소드를 실행합니다.
     *
     * @param id - 반환 할 카테고리의 식별번호입니다.
     * @return 지정한 카테고리의 정보를 담은 객체를 반환합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    CategoryRetrieveResponse retrieveCategory(final String id) throws UnAuthenticException, UnAuthorizationException;

    /**
     * 카테고리 분류 전체를 반환하는 Adapter 메소드를 실행합니다.
     *
     * @return 카테고리 분류 전체를 담은 List 를 반환합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    List<CategorizationRetrieveResponse> retrieveCategorizations()
            throws UnAuthenticException, UnAuthorizationException;

    /**
     * 카테고리 전체를 반환하는 Adapter 메소드를 실행합니다.
     *
     * @return 카테고리 전체를 담은 List 를 반환합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    List<CategoryRetrieveResponse> retrieveCategories() throws UnAuthenticException, UnAuthorizationException;

    /**
     * 상품에 해당하는 카테고리만을 조회합니다.
     *
     * @return - 상품에 해당하는 카테고리를 반환합니다.
     */
    List<CategoryRetrieveResponse> retrieveCategoriesOnlyProducts();

    /**
     * 지정한 카테고리를 입력받은 정보로 수정하는 Adapter 메소드를 실행합니다.
     *
     * @param categoryId      - 수정할 카테고리의 식별번호입니다.
     * @param categoryRequest - 카테고리 수정을 위한 입력정보를 담은 객체입니다.
     * @throws JsonProcessingException  Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    void updateCategory(final String categoryId, final CategoryUpdateRequest categoryRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException;

    /**
     * 지정한 카테고리를 삭제하는 Adapter 메소드를 실행합니다.
     *
     * @param categoryId - 삭제할 카테고리의 식별번호입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    void deleteCategory(final String categoryId) throws UnAuthenticException, UnAuthorizationException;

}
