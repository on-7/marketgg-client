package com.nhnacademy.marketgg.client.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
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
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.service.CartService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class CartControllerTest {

    MockMvc mockMvc;

    @Autowired
    CartController controller;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    RedisTemplate<String, JwtInfo> redisTemplate;

    @MockBean
    CartService cartService;

    @Value("${gg.jwt.test.user}")
    String jwt;

    static ProductToCartRequest productToCartRequest;

    @BeforeAll
    static void beforeAll() {
        productToCartRequest = new ProductToCartRequest();
        ReflectionTestUtils.setField(productToCartRequest, "id", 1L);
        ReflectionTestUtils.setField(productToCartRequest, "amount", 10);
    }

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                                 .alwaysDo(print())
                                 .build();
    }

    @Test
    @DisplayName("장바구니에 상품 추가")
    void addToProduct() throws Exception {

        String request = mapper.writeValueAsString(productToCartRequest);
        String sessionId = UUID.randomUUID().toString();
        JwtInfo jwtInfo = new JwtInfo(jwt, LocalDateTime.now().plus(1, ChronoUnit.DAYS).toString());
        redisTemplate.opsForHash().put(sessionId, JwtInfo.JWT_REDIS_KEY, jwtInfo);

        willDoNothing().given(cartService).addProduct(any(ProductToCartRequest.class));

        mockMvc.perform(post("/cart")
                   .cookie(new Cookie(JwtInfo.SESSION_ID, sessionId))
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(request))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success", equalTo(true)))
               .andExpect(jsonPath("$.data", equalTo(true)));

        then(cartService).should(times(1)).addProduct(any(ProductToCartRequest.class));
        redisTemplate.opsForHash().delete(sessionId, JwtInfo.JWT_REDIS_KEY);
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

        then(cartService).should(times(1)).retrieveCarts();
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

        then(cartService).should(times(1)).updateAmount(any(ProductToCartRequest.class));
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

        then(cartService).should(times(1)).deleteProducts(anyList());
    }

}
