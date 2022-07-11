package com.nhnacademy.marketgg.client.web;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AdminCategoryController.class)
public class AdminCategoryControllerTest {
    
}
