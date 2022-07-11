package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.CategoryRetrieveResponse;

import java.util.List;

public interface CategoryService {

    void createCategory(final CategoryCreateRequest categoryRequest) throws JsonProcessingException;

    List<CategoryRetrieveResponse> retrieveCategories();

    void updateCategory(final String categoryId, final CategoryUpdateRequest categoryRequest)
            throws JsonProcessingException;

    void deleteCategory(final String categoryId);

}
