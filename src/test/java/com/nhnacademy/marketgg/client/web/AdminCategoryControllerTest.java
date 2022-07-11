package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AdminCategoryController.class)
public class AdminCategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryService categoryService;

    @Test
    @DisplayName("카테고리 관리 메인 페이지 이동")
    void testIndex() throws Exception {
        mockMvc.perform(get("/admin/v1/categories/index"))
               .andExpect(view().name("/categories/index"));
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
