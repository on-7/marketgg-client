package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AdminCategoryController.class)
public class AdminCategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CategoryService categoryService;

    @Test
    @DisplayName("카테고리 등록 페이지 이동")
    void testDoCreateCategory() throws Exception {
        mockMvc.perform(get("/admin/v1/categories/create"))
               .andExpect(view().name("/categories/create-form"));
    }

    @Test
    @DisplayName("카테고리 등록")
    void testCreateCategory() throws Exception {
        CategoryCreateRequest categoryRequest = new CategoryCreateRequest();
        ReflectionTestUtils.setField(categoryRequest, "categoryCode", "001001");
        ReflectionTestUtils.setField(categoryRequest, "categorizationCode", "001");
        ReflectionTestUtils.setField(categoryRequest, "name", "친환경");
        ReflectionTestUtils.setField(categoryRequest, "sequence", 1);

        String content = objectMapper.writeValueAsString(categoryRequest);

        doNothing().when(categoryService).createCategory(any(CategoryCreateRequest.class));

        mockMvc.perform(post("/admin/v1/categories")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().is3xxRedirection());

        verify(categoryService, times(1)).createCategory(any(CategoryCreateRequest.class));
    }

    @DisplayName("카테고리 전체 목록 조회")
    void testRetrieveCategories() throws Exception {
        when(categoryService.retrieveCategories()).thenReturn(List.of(new CategoryRetrieveResponse()));

        mockMvc.perform(get("/admin/v1/categories/index"))
               .andExpect(status().isOk())
               .andExpect(view().name("/categories/index"));
    }

    @Test
    @DisplayName("카테고리 수정 페이지 이동")
    void testDoUpdateCategory() throws Exception {
        mockMvc.perform(get("/admin/v1/categories/update"))
               .andExpect(view().name("/categories/update-form"));
    }

    @Test
    @DisplayName("카테고리 수정")
    void testUpdateCategory() throws Exception {
        CategoryUpdateRequest categoryRequest = new CategoryUpdateRequest();
        ReflectionTestUtils.setField(categoryRequest, "categorizationCode", "001");
        ReflectionTestUtils.setField(categoryRequest, "name", "콩나물");
        ReflectionTestUtils.setField(categoryRequest, "sequence", 1);

        String content = objectMapper.writeValueAsString(categoryRequest);

        doNothing().when(categoryService).updateCategory(anyString(), any(CategoryUpdateRequest.class));

        mockMvc.perform(put("/admin/v1/categories/" + "001001")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().is3xxRedirection());

        verify(categoryService, times(1))
                .updateCategory(anyString(), any(CategoryUpdateRequest.class));
    }

    @Test
    @DisplayName("카테고리 삭제")
    void testDeleteCategory() throws Exception {
        doNothing().when(categoryService).deleteCategory(anyString());

        mockMvc.perform(delete("/admin/v1/categories/{categoryId}", "001"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/admin/v1/categories/index"));

        verify(categoryService, times(1)).deleteCategory("001");
    }

}
