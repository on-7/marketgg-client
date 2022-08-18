package com.nhnacademy.marketgg.client.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.service.CategoryService;
import com.nhnacademy.marketgg.client.web.admin.AdminCategoryController;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AdminCategoryController.class)
class AdminCategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    RedisTemplate<String, JwtInfo> redisTemplate;

    @MockBean
    CategoryService categoryService;

    private static final String DEFAULT_CATEGORY = "/admin/categories";

    @Test
    @DisplayName("카테고리 등록 페이지 이동")
    void testDoCreateCategory() throws Exception {
        this.mockMvc.perform(get(DEFAULT_CATEGORY + "/create"))
                    .andExpect(view().name("/categories/create-form"));
    }

    @Test
    @DisplayName("카테고리 등록")
    void testCreateCategory() throws Exception {
        CategoryCreateRequest categoryRequest =
                new CategoryCreateRequest("001001", "001", "친환경", 1);

        String content = objectMapper.writeValueAsString(categoryRequest);

        willDoNothing().given(categoryService).createCategory(any(CategoryCreateRequest.class));

        this.mockMvc.perform(post(DEFAULT_CATEGORY)
                                     .content(content)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is3xxRedirection());

        then(categoryService).should(times(1)).createCategory(any(CategoryCreateRequest.class));
    }

    @Test
    @DisplayName("카테고리 전체 목록 조회")
    void testRetrieveCategories() throws Exception {
        given(categoryService.retrieveCategories()).willReturn(List.of(new CategoryRetrieveResponse()));

        this.mockMvc.perform(get(DEFAULT_CATEGORY + "/index"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("/categories/index"));

        then(categoryService).should(times(1)).retrieveCategories();
    }

    @Test
    @DisplayName("카테고리 수정 페이지 이동")
    void testDoUpdateCategory() throws Exception {
        CategoryRetrieveResponse categoryResponse = new CategoryRetrieveResponse();
        ReflectionTestUtils.setField(categoryResponse, "categoryid", "001");
        ReflectionTestUtils.setField(categoryResponse, "categorizationName", "제품");
        ReflectionTestUtils.setField(categoryResponse, "categoryName", "친환경");
        ReflectionTestUtils.setField(categoryResponse, "sequence", 1);

        given(categoryService.retrieveCategory(anyString())).willReturn(categoryResponse);

        this.mockMvc.perform(get(DEFAULT_CATEGORY + "/update/{categoryId}", "001"))
                    .andExpect(view().name("/categories/update-form"));

        then(categoryService).should(times(1)).retrieveCategory(anyString());
    }

    @Test
    @DisplayName("카테고리 수정")
    void testUpdateCategory() throws Exception {
        CategoryUpdateRequest categoryRequest = new CategoryUpdateRequest("001", "콩나물", 1);

        String content = objectMapper.writeValueAsString(categoryRequest);

        willDoNothing().given(categoryService).updateCategory(anyString(), any(CategoryUpdateRequest.class));

        this.mockMvc.perform(put(DEFAULT_CATEGORY + "/001001")
                                     .content(content)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is3xxRedirection());

        then(categoryService).should(times(1)).updateCategory(anyString(), any(CategoryUpdateRequest.class));
    }

    @Test
    @DisplayName("카테고리 삭제")
    void testDeleteCategory() throws Exception {
        willDoNothing().given(categoryService).deleteCategory(anyString());

        this.mockMvc.perform(delete(DEFAULT_CATEGORY + "/{categoryId}", "001"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + DEFAULT_CATEGORY + "/index"));

        then(categoryService).should(times(1)).deleteCategory("001");
    }

}
