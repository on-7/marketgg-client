package com.nhnacademy.marketgg.client.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.marketgg.client.dto.PageResult;
import com.nhnacademy.marketgg.client.dto.request.SearchRequestForCategory;
import com.nhnacademy.marketgg.client.dto.response.ImageResponse;
import com.nhnacademy.marketgg.client.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.dto.response.SearchProductResponse;
import com.nhnacademy.marketgg.client.dummy.Dummy;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.service.ImageService;
import com.nhnacademy.marketgg.client.service.ProductService;
import com.nhnacademy.marketgg.client.web.product.ProductController;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @MockBean
    RedisTemplate<String, JwtInfo> redisTemplate;

    @MockBean
    ImageService imageService;

    private static final String DEFAULT_PRODUCT = "/products";

    private SearchProductResponse response;
    private ImageResponse imageResponse;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        response = new SearchProductResponse();
        imageResponse = Dummy.getDummyImageResponse();
        productResponse = Dummy.getDummyProductResponse();
    }

    @Test
    @DisplayName("카테고리 목록에서 상품 검색")
    void testSearchProductListByCategory() throws Exception {
        given(productService.searchProductListByCategory(any(SearchRequestForCategory.class))).willReturn(
            List.of(response));

        this.mockMvc.perform(get(DEFAULT_PRODUCT + "/search", "100")
                                 .param("categoryId", "001")
                                 .param("keyword", "안녕")
                                 .param("page", "0"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("products/index"));

        then(productService).should(times(1)).searchProductListByCategory(any(SearchRequestForCategory.class));
    }

    @Test
    @DisplayName("카테고리 목록 내에서 가격 옵션 별 검색")
    void testSearchProductListByPrice() throws Exception {
        given(productService.searchProductListByPrice(any(SearchRequestForCategory.class), anyString())).willReturn(
            List.of(response));

        this.mockMvc.perform(get(DEFAULT_PRODUCT + "/categories/{categoryId}/price/{option}/search", "100", "asc")
                                 .param("keyword", "안녕")
                                 .param("page", "0"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("products/index"));

        then(productService).should(times(1))
                            .searchProductListByPrice(any(SearchRequestForCategory.class), anyString());
    }

    @Test
    @DisplayName("상품 전체조회 테스트")
    void testRetrieveProducts() throws Exception {
        PageResult<ProductResponse> dummyPageResult = Dummy.getDummyPageResult();
        given(productService.retrieveProducts(anyInt())).willReturn(dummyPageResult);
        given(imageService.retrieveImage(anyLong())).willReturn(imageResponse);

        ResultActions resultActions = this.mockMvc.perform(get(DEFAULT_PRODUCT + "?page=0"));

        MvcResult mvcResult =
            resultActions.andExpect(status().isOk()).andExpect(view().name("index")).andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel()
                          .get("products")).isNotNull();

        then(productService).should(times(1)).retrieveProducts(anyInt());
    }

    @Test
    @DisplayName("상품 상세조회 테스트")
    void testRetrieveProductDetails() throws Exception {
        given(productService.retrieveProductDetails(anyLong())).willReturn(productResponse);
        given(imageService.retrieveImage(anyLong())).willReturn(imageResponse);

        ResultActions resultActions =
            this.mockMvc.perform(get(DEFAULT_PRODUCT + "/{productId}", 1));

        MvcResult mvcResult =
            resultActions.andExpect(status().isOk()).andExpect(view().name("products/product-view"))
                         .andReturn();

        assertThat(mvcResult.getModelAndView().getModel().get("productDetails")).isNotNull();
        then(productService).should(times(1)).retrieveProductDetails(anyLong());
    }


}
