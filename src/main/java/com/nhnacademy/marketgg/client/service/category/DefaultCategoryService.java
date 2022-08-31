package com.nhnacademy.marketgg.client.service.category;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.category.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.category.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.category.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.category.CategoryUpdateRequest;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.category.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void createCategory(final CategoryCreateRequest categoryRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        categoryRepository.createCategory(categoryRequest);
    }

    @Override
    public CategoryRetrieveResponse retrieveCategory(String id) throws UnAuthenticException, UnAuthorizationException {
        return categoryRepository.retrieveCategory(id);
    }

    @Override
    public List<CategoryRetrieveResponse> retrieveCategories() throws UnAuthenticException, UnAuthorizationException {
        return categoryRepository.retrieveCategories();
    }

    @Override
    public List<CategorizationRetrieveResponse> retrieveCategorizations()
            throws UnAuthenticException, UnAuthorizationException {
        return categoryRepository.retrieveCategorizations();
    }

    @Override
    public void updateCategory(final String id, final CategoryUpdateRequest categoryRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        categoryRepository.updateCategory(id, categoryRequest);
    }

    @Override
    public void deleteCategory(final String id) throws UnAuthenticException, UnAuthorizationException {
        categoryRepository.deleteCategory(id);
    }

}
