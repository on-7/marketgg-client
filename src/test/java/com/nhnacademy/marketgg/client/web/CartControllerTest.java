package com.nhnacademy.marketgg.client.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.client.service.CartService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(CartController.class)
class CartControllerTest {

    MockMvc mockMvc;

    @Autowired
    CartController controller;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    CartService cartService;

    static ProductToCartRequest productToCartRequest;

    @BeforeAll
    static void beforeAll() {
        productToCartRequest = new ProductToCartRequest();
        ReflectionTestUtils.setField(productToCartRequest, "id", 1L);
        ReflectionTestUtils.setField(productToCartRequest, "amount", 10);
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                                 .alwaysDo(print())
                                 .build();
    }

    @Test
    @DisplayName("장바구니에 상품 추가")
    void addToProduct() throws Exception {

        willDoNothing().given(cartService).addProduct(any(ProductToCartRequest.class));

        String request = mapper.writeValueAsString(productToCartRequest);

        mockMvc.perform(post("/cart")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(request))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success", equalTo(true)))
               .andExpect(jsonPath("$.data", equalTo(true)));
    }

    @Test
    @DisplayName("장바구니 조회")
    void retrieveCart() throws Exception {
        given(cartService.retrieveCarts()).willReturn(new ArrayList<>());

        MvcResult mvcResult = mockMvc.perform(get("/cart"))
                                     .andExpect(status().isOk())
                                     .andExpect(view().name("carts/index"))
                                     .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("cart"))
            .isNotNull();
    }

    @Test
    @DisplayName("장바구니 상품 수량 변경")
    void updateAmount() throws Exception {
        willDoNothing().given(cartService).updateAmount(any(ProductToCartRequest.class));

        String request = mapper.writeValueAsString(productToCartRequest);

        mockMvc.perform(patch("/cart")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(request))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success", equalTo(true)))
               .andExpect(jsonPath("$.data", equalTo(true)));
    }

    @Test
    @DisplayName("장바구니 상품 삭제")
    void deleteProduct() throws Exception {
        willDoNothing().given(cartService).deleteProducts(anyList());

        String request = mapper.writeValueAsString(List.of(1, 2, 3));

        mockMvc.perform(delete("/cart")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(request))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success", equalTo(true)))
               .andExpect(jsonPath("$.data", equalTo(true)));
    }

}
