package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CategoryUpdateRequest;

public interface CategoryService {

    void updateCategory(final String categoryId, final CategoryUpdateRequest categoryRequest)
            throws JsonProcessingException;

}
