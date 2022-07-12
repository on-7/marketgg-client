package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.adapter.CategoryAdapter;
import com.nhnacademy.marketgg.client.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {

    private final CategoryAdapter categoryAdapter;

    @Override
    public void createCategory(final CategoryCreateRequest categoryRequest)
            throws JsonProcessingException {

        categoryAdapter.createCategory(categoryRequest);
    }

    @Override
    public List<CategoryRetrieveResponse> retrieveCategories() {
        return categoryAdapter.retrieveCategories();
    }

    @Override
    public void updateCategory(final String id, final CategoryUpdateRequest categoryRequest)
            throws JsonProcessingException {

        categoryAdapter.updateCategory(id, categoryRequest);
    }

    @Override
    public void deleteCategory(final String id) {
        categoryAdapter.deleteCategory(id);
    }

}
