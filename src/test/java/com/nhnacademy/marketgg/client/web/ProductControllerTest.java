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

import com.nhnacademy.marketgg.client.config.RedisConfig;
import com.nhnacademy.marketgg.client.dto.PageResult;
import com.nhnacademy.marketgg.client.dto.request.SearchRequestForCategory;
import com.nhnacademy.marketgg.client.dto.response.ImageResponse;
import com.nhnacademy.marketgg.client.dto.response.ProductListResponse;
import com.nhnacademy.marketgg.client.dummy.Dummy;
import com.nhnacademy.marketgg.client.service.ImageService;
import com.nhnacademy.marketgg.client.service.ProductService;
import com.nhnacademy.marketgg.client.service.ReviewService;
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
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureMockMvc(addFilters = false)
@Import(RedisConfig.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @MockBean
    ImageService imageService;

    @MockBean
    ReviewService reviewService;

    private static final String DEFAULT_PRODUCT = "/products";

    private ProductListResponse response;
    private ImageResponse imageResponse;

    private PageResult<ProductListResponse> pageResult;

    @BeforeEach
    void setUp() {
        response = new ProductListResponse();
        imageResponse = Dummy.getDummyImageResponse();
        pageResult = new PageResult<>();

        ReflectionTestUtils.setField(response, "id", 1L);
        ReflectionTestUtils.setField(response, "categoryCode", "001");
        ReflectionTestUtils.setField(response, "productName", "안녕");
        ReflectionTestUtils.setField(response, "content", "안녕");
        ReflectionTestUtils.setField(response, "description", "<p>안녕<p>");
        ReflectionTestUtils.setField(response, "labelName", "라벨입니다");
        ReflectionTestUtils.setField(response, "imageAddress", "http~");
        ReflectionTestUtils.setField(response, "price", 1000L);
        ReflectionTestUtils.setField(response, "amount", 100L);

        ReflectionTestUtils.setField(pageResult, "pageNumber", 0);
        ReflectionTestUtils.setField(pageResult, "pageSize", 10);
        ReflectionTestUtils.setField(pageResult, "totalPages", 0);
        ReflectionTestUtils.setField(pageResult, "data", List.of(response));
    }

    @Test
    @DisplayName("카테고리 목록에서 상품 검색")
    void testSearchProductListByCategory() throws Exception {
        given(productService.searchProductListByCategory(any(SearchRequestForCategory.class))).willReturn(pageResult);

        this.mockMvc.perform(get(DEFAULT_PRODUCT + "/search", "100")
                                     .param("categoryId", "001")
                                     .param("keyword", "안녕")
                                     .param("page", "0"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("index"));

        then(productService).should(times(1)).searchProductListByCategory(any(SearchRequestForCategory.class));
    }

    @Test
    @DisplayName("카테고리 목록 내에서 가격 옵션 별 검색")
    void testSearchProductListByPrice() throws Exception {
        given(productService.searchProductListByPrice(any(SearchRequestForCategory.class), anyString())).willReturn(
                pageResult);

        this.mockMvc.perform(get(DEFAULT_PRODUCT + "/categories/{categoryId}/price/{option}/search", "100", "asc")
                                     .param("keyword", "안녕")
                                     .param("page", "0"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("index"));

        then(productService).should(times(1))
                            .searchProductListByPrice(any(SearchRequestForCategory.class), anyString());
    }

    @Test
    @DisplayName("상품 전체조회 테스트")
    void testRetrieveProducts() throws Exception {
        PageResult<ProductListResponse> dummyPageResult = Dummy.getDummyPageResult();
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
    @DisplayName("상품 추천 목록 조회 테스트")
    void testSuggestionProductList() throws Exception {
        given(productService.searchProductListByCategory(any(SearchRequestForCategory.class))).willReturn(pageResult);

        this.mockMvc.perform(get(DEFAULT_PRODUCT + "/suggest")
                                     .param("keyword", "dd")
                                     .param("page", "0"))
                .andExpect(status().isOk());

        then(productService).should(times(1)).searchProductListByCategory(any(SearchRequestForCategory.class));
    }

    @Test
    @DisplayName("상품 추천 목록 조회 테스트 X 10")
    void testSuggestionProductListForTen() throws Exception {
        ReflectionTestUtils.setField(pageResult, "data", List.of(response, response, response, response, response
        ,response, response, response, response, response));
        given(productService.searchProductListByCategory(any(SearchRequestForCategory.class))).willReturn(pageResult);

        this.mockMvc.perform(get(DEFAULT_PRODUCT + "/suggest")
                                     .param("keyword", "dd")
                                     .param("page", "0"))
                    .andExpect(status().isOk());

        then(productService).should(times(1)).searchProductListByCategory(any(SearchRequestForCategory.class));
    }

//    @Test
//    @DisplayName("상품 상세조회 테스트")
//    void testRetrieveProductDetails() throws Exception {
//        given(productService.retrieveProductDetails(anyLong())).willReturn();
//        given(imageService.retrieveImage(anyLong())).willReturn(imageResponse);
//        given(reviewService.retrieveReviews(anyLong())).willReturn()
//
//        ResultActions resultActions =
//            this.mockMvc.perform(get(DEFAULT_PRODUCT + "/{productId}", 1));
//
//        MvcResult mvcResult =
//            resultActions.andExpect(status().isOk()).andExpect(view().name("products/product-view"))
//                         .andReturn();
//
//        assertThat(mvcResult.getModelAndView().getModel().get("productDetails")).isNotNull();
//        then(productService).should(times(1)).retrieveProductDetails(anyLong());
//    }

}
