package com.nhnacademy.marketgg.client.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.config.RedisConfig;
import com.nhnacademy.marketgg.client.dto.product.ProductCreateRequest;
import com.nhnacademy.marketgg.client.dto.product.ProductUpdateRequest;
import com.nhnacademy.marketgg.client.dto.category.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.image.ImageResponse;
import com.nhnacademy.marketgg.client.dto.label.LabelRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.product.ProductResponse;
import com.nhnacademy.marketgg.client.dummy.Dummy;
import com.nhnacademy.marketgg.client.service.category.CategoryService;
import com.nhnacademy.marketgg.client.service.image.ImageService;
import com.nhnacademy.marketgg.client.service.label.LabelService;
import com.nhnacademy.marketgg.client.service.product.ProductInquiryService;
import com.nhnacademy.marketgg.client.service.product.ProductService;
import com.nhnacademy.marketgg.client.web.admin.AdminProductController;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.web.multipart.MultipartFile;

@AutoConfigureMockMvc(addFilters = false)
@Import(RedisConfig.class)
@WebMvcTest(AdminProductController.class)
class AdminProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    @MockBean
    ProductInquiryService productInquiryService;

    @MockBean
    CategoryService categoryService;

    @MockBean
    LabelService labelService;

    @MockBean
    ImageService imageService;

    private ProductResponse productResponse;
    private ImageResponse imageResponse;
    private CategoryRetrieveResponse categoryRetrieveResponse;
    private LabelRetrieveResponse labelRetrieveResponse;

    private static final String DEFAULT_PRODUCT_URI = "/admin/products";

    HttpHeaders httpHeaders;

    @BeforeEach
    void setUp() {
        productResponse = Dummy.getDummyProductResponse();
        imageResponse = Dummy.getDummyImageResponse();
        categoryRetrieveResponse = Dummy.getDummyCategoryResponse();
        labelRetrieveResponse = Dummy.getDummyLabelResponse();

        httpHeaders = new HttpHeaders();
        httpHeaders.add("AUTH-ID", UUID.randomUUID().toString());
        httpHeaders.add("WWW-Authenticate", "[\"ROLE_ADMIN\"]");
    }

    @Test
    @DisplayName("상품 등록 테스트")
    void testCreateProduct() throws Exception {
        willDoNothing().given(productService)
                       .createProduct(any(MultipartFile.class), any(ProductCreateRequest.class));

        MockMultipartFile image = getImage();

        ResultActions resultActions =
            this.mockMvc.perform(multipart(DEFAULT_PRODUCT_URI).file(image)
                                                               .headers(httpHeaders)
                                                               .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                                                               .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                               .content(
                                                                   Dummy.getDummyModelAttributeProductCreateRequest()));

        MvcResult mvcResult = resultActions.andExpect(status().is3xxRedirection()).andExpect(view().name(
            "redirect:/")).andReturn();

        assertThat(mvcResult.getModelAndView()).isNotNull();
        then(productService).should(times(1))
                            .createProduct(any(MultipartFile.class), any(ProductCreateRequest.class));
    }

    @Test
    @DisplayName("상품 등록페이지 조회 테스트")
    void testCreateProductPage() throws Exception {
        given(categoryService.retrieveCategories()).willReturn(List.of(categoryRetrieveResponse));
        given(labelService.retrieveLabels()).willReturn(List.of(labelRetrieveResponse));

        ResultActions resultActions = this.mockMvc.perform(get(DEFAULT_PRODUCT_URI + "/create"));

        MvcResult mvcResult = resultActions.andExpect(status().isOk())
                                           .andExpect(view().name("pages/admin/products/product-create-form"))
                                           .andReturn();

        assertThat(mvcResult.getModelAndView().getModel().get("categories")).isNotNull();
        assertThat(mvcResult.getModelAndView().getModel().get("labels")).isNotNull();

    }

    @Test
    @DisplayName("상품 수정 테스트")
    void testUpdateProduct() throws Exception {
        willDoNothing().given(productService).updateProduct(anyLong(),
            any(MockMultipartFile.class),
            any(ProductUpdateRequest.class));

        MockMultipartFile image = getImage();

        MockMultipartHttpServletRequestBuilder builder = multipart(DEFAULT_PRODUCT_URI + "/{productId}", 1);
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        ResultActions resultActions = this.mockMvc.perform(builder.file(image).headers(httpHeaders)
                                                                  .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                                                                  .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                                  .content(
                                                                      Dummy.getDummyModelAttributeProductUpdateRequest()));

        MvcResult mvcResult = resultActions.andExpect(status().is3xxRedirection())
                                           .andExpect(view().name(
                                               "redirect:/"))
                                           .andReturn();

        assertThat(mvcResult.getModelAndView()).isNotNull();
        then(productService).should(times(1)).updateProduct(anyLong(),
            any(MockMultipartFile.class),
            any(ProductUpdateRequest.class));
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteProduct() throws Exception {
        willDoNothing().given(productService).deleteProduct(anyLong());

        ResultActions resultActions = this.mockMvc.perform(delete(DEFAULT_PRODUCT_URI + "/{productId}", 1)
            .headers(httpHeaders));

        MvcResult mvcResult = resultActions.andExpect(status().is3xxRedirection())
                                           .andExpect(view().name(
                                               "redirect:/"))
                                           .andReturn();

        assertThat(mvcResult.getModelAndView()).isNotNull();
        then(productService).should(times(1)).deleteProduct(anyLong());
    }

    private MockMultipartFile getImage() throws IOException {
        URL url = getClass().getClassLoader().getResource("lee.png");
        String filePath = Objects.requireNonNull(url).getPath();

        return new MockMultipartFile("image", "test.png", "image/png", new FileInputStream(filePath));
    }

}
