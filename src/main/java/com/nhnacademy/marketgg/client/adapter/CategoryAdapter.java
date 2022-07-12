package com.nhnacademy.marketgg.client.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.response.CategoryRetrieveResponse;
import java.util.List;

public interface CategoryAdapter {

    void createCategory(final CategoryCreateRequest categoryRequest) throws JsonProcessingException;

    List<CategoryRetrieveResponse> retrieveCategories();

    List<CategorizationRetrieveResponse> retrieveCategorizations();

    void updateCategory(final String id, final CategoryUpdateRequest categoryRequest)
            throws JsonProcessingException;

    void deleteCategory(final String id);

}
