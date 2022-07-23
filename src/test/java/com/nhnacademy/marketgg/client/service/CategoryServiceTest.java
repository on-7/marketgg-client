package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.repository.CategoryRepository;
import com.nhnacademy.marketgg.client.service.impl.DefaultCategoryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

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
    void testCreateCategory() throws JsonProcessingException {
        willDoNothing().given(categoryRepository).createCategory(any(CategoryCreateRequest.class));

        categoryService.createCategory(categoryCreateRequest);

        then(categoryRepository).should().createCategory(any(CategoryCreateRequest.class));
    }

    @Test
    @DisplayName("카테고리 단건 조회")
    void testRetrieveCategory() {
        given(categoryRepository.retrieveCategory(anyString())).willReturn(any(CategoryRetrieveResponse.class));

        categoryService.retrieveCategory("101");

        then(categoryRepository).should().retrieveCategory(anyString());
    }

    @Test
    @DisplayName("모든 카테고리 목록 조회")
    void testRetrieveCategories() {
        given(categoryRepository.retrieveCategories()).willReturn(List.of());

        categoryService.retrieveCategories();

        then(categoryRepository).should().retrieveCategories();
    }

    @Test
    @DisplayName("모든 카테고리 분류표 목록 조회")
    void testRetrieveCategorizations() {
        given(categoryRepository.retrieveCategorizations()).willReturn(List.of());

        categoryService.retrieveCategorizations();

        then(categoryRepository).should().retrieveCategorizations();
    }

    @Test
    @DisplayName("카테고리 수정")
    void testUpdateCategory() throws JsonProcessingException {
        willDoNothing().given(categoryRepository).updateCategory(anyString(), any(CategoryUpdateRequest.class));

        categoryService.updateCategory("101", categoryUpdateRequest);

        then(categoryRepository).should().updateCategory(anyString(), any(CategoryUpdateRequest.class));
    }

    @Test
    @DisplayName("카테고리 삭제")
    void testDeleteCategory() {
        willDoNothing().given(categoryRepository).deleteCategory(anyString());

        categoryService.deleteCategory("101");

        then(categoryRepository).should().deleteCategory(anyString());
    }

}
