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
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @CacheEvict(cacheNames = "category", allEntries = true)
    public void createCategory(final CategoryCreateRequest categoryRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {
        log.info("카테고리 캐시가 제거되었습니다.");
        categoryRepository.createCategory(categoryRequest);
    }

    @Override
    public CategoryRetrieveResponse retrieveCategory(String id) throws UnAuthenticException, UnAuthorizationException {
        return categoryRepository.retrieveCategory(id);
    }

    @Override
    @Cacheable(cacheNames = "category")
    public List<CategoryRetrieveResponse> retrieveCategories() throws UnAuthenticException, UnAuthorizationException {
        log.info("카테고리가 캐싱되었습니다.");
        return categoryRepository.retrieveCategories();
    }

    @Override
    @Cacheable(cacheNames = "category")
    public List<CategoryRetrieveResponse> retrieveCategoriesOnlyProducts() {
        log.info("카테고리가 캐싱되었습니다.");
        return categoryRepository.retrieveCategoriesOnlyProducts();
    }

    @Override
    public List<CategorizationRetrieveResponse> retrieveCategorizations()
            throws UnAuthenticException, UnAuthorizationException {
        return categoryRepository.retrieveCategorizations();
    }

    @Override
    @CacheEvict(cacheNames = "category", allEntries = true)
    public void updateCategory(final String id, final CategoryUpdateRequest categoryRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {
        log.info("카테고리 캐시가 제거되었습니다.");
        categoryRepository.updateCategory(id, categoryRequest);
    }

    @Override
    @CacheEvict(cacheNames = "category", allEntries = true)
    public void deleteCategory(final String id) throws UnAuthenticException, UnAuthorizationException {
        log.info("카테고리 캐시가 제거되었습니다.");
        categoryRepository.deleteCategory(id);
    }

}
