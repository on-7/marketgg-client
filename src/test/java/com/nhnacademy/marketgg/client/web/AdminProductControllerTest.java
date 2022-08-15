package com.nhnacademy.marketgg.client.web;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.service.ProductService;
import com.nhnacademy.marketgg.client.web.admin.AdminPointController;
import com.nhnacademy.marketgg.client.web.admin.AdminProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AdminProductController.class)
class AdminProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @MockBean
    RedisTemplate<String, JwtInfo> redisTemplate;

    @Test
    void createProduct() {

    }

    @Test
    void testCreateProduct() {
    }

    @Test
    void retrieveProducts() {
    }

    @Test
    void retrieveProductDetails() {
    }

    @Test
    void retrieveProductsByCategory() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void testUpdateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}