package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CategoryUpdateRequest;

public interface CategoryService {

    void updateCategory(String categoryId, CategoryUpdateRequest categoryRequest) throws JsonProcessingException;

}
