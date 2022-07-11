package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.dto.response.CategoryRetrieveResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryRetrieveResponse> retrieveCategories();
}
