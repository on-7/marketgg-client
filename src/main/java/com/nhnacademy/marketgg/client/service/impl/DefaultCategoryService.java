package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.repository.CategoryRepository;
import com.nhnacademy.marketgg.client.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void createCategory(final CategoryCreateRequest categoryRequest)
        throws JsonProcessingException {

        categoryRepository.createCategory(categoryRequest);
    }

    @Override
    public CategoryRetrieveResponse retrieveCategory(String id) {
        return categoryRepository.retrieveCategory(id);
    }

    @Override
    public List<CategoryRetrieveResponse> retrieveCategories() {
        return categoryRepository.retrieveCategories();
    }

    @Override
    public List<CategorizationRetrieveResponse> retrieveCategorizations() {
        return categoryRepository.retrieveCategorizations();
    }

    @Override
    public void updateCategory(final String id, final CategoryUpdateRequest categoryRequest)
        throws JsonProcessingException {

        categoryRepository.updateCategory(id, categoryRequest);
    }

    @Override
    public void deleteCategory(final String id) {
        categoryRepository.deleteCategory(id);
    }

}
