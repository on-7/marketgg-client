package com.nhnacademy.marketgg.client.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.category.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.category.CategoryUpdateRequest;
import com.nhnacademy.marketgg.client.dto.category.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.category.CategoryRepository;
import com.nhnacademy.marketgg.client.service.category.DefaultCategoryService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private DefaultCategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private static CategoryCreateRequest categoryCreateRequest;
    private static CategoryUpdateRequest categoryUpdateRequest;

    @BeforeAll
    static void beforeAll() {
        categoryCreateRequest = new CategoryCreateRequest("101", "100", "categoryName", 1);
        categoryUpdateRequest = new CategoryUpdateRequest("101", "categoryName", 1);
    }

    @Test
    @DisplayName("카테고리 등록")
    void testCreateCategory() throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {
        willDoNothing().given(categoryRepository).createCategory(any(CategoryCreateRequest.class));

        categoryService.createCategory(categoryCreateRequest);

        then(categoryRepository).should(times(1)).createCategory(any(CategoryCreateRequest.class));
    }

    @Test
    @DisplayName("카테고리 단건 조회")
    void testRetrieveCategory() throws UnAuthenticException, UnAuthorizationException {
        given(categoryRepository.retrieveCategory(anyString())).willReturn(any(CategoryRetrieveResponse.class));

        categoryService.retrieveCategory("101");

        then(categoryRepository).should(times(1)).retrieveCategory(anyString());
    }

    @Test
    @DisplayName("모든 카테고리 목록 조회")
    void testRetrieveCategories() throws UnAuthenticException, UnAuthorizationException {
        given(categoryRepository.retrieveCategories()).willReturn(List.of());

        categoryService.retrieveCategories();

        then(categoryRepository).should(times(1)).retrieveCategories();
    }

    @Test
    @DisplayName("모든 카테고리 분류표 목록 조회")
    void testRetrieveCategorizations() throws UnAuthenticException, UnAuthorizationException {
        given(categoryRepository.retrieveCategorizations()).willReturn(List.of());

        categoryService.retrieveCategorizations();

        then(categoryRepository).should(times(1)).retrieveCategorizations();
    }

    @Test
    @DisplayName("카테고리 수정")
    void testUpdateCategory() throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {
        willDoNothing().given(categoryRepository).updateCategory(anyString(), any(CategoryUpdateRequest.class));

        categoryService.updateCategory("101", categoryUpdateRequest);

        then(categoryRepository).should(times(1)).updateCategory(anyString(), any(CategoryUpdateRequest.class));
    }

    @Test
    @DisplayName("카테고리 삭제")
    void testDeleteCategory() throws UnAuthenticException, UnAuthorizationException {
        willDoNothing().given(categoryRepository).deleteCategory(anyString());

        categoryService.deleteCategory("101");

        then(categoryRepository).should(times(1)).deleteCategory(anyString());
    }

}
