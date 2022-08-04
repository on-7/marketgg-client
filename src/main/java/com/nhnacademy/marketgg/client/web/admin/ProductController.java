package com.nhnacademy.marketgg.client.web.admin;

import com.nhnacademy.marketgg.client.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductModifyRequest;
import com.nhnacademy.marketgg.client.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.response.LabelRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.service.CategoryService;
import com.nhnacademy.marketgg.client.service.LabelService;
import com.nhnacademy.marketgg.client.service.ProductService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 상품 컨트롤러 입니다.
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final LabelService labelService;

    private final static String DEFAULT_PRODUCT_URI = "/admin/products";

    /**
     * 기본 상품 index 페이지 GetMapping을 지원합니다.
     *
     * @return - 기본 뷰 페이지를 리턴합니다.
     * @since 1.0.0
     */
    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("products/index");
    }

    /**
     * 상품 생성을 위한 PostMapping 을 지원 합니다.
     *
     * @param image          - 사진은 MultipartFile 타입입니다.
     * @param productRequest - 상품 생성을 위한 DTO 입니다.
     * @return - 기본 뷰 페이지를 리턴합니다.
     * @throws IOException 파일 입출력에 대한 에러처리입니다.
     * @since 1.0.0
     */
    @PostMapping("/create")
    public ModelAndView createProduct(@RequestPart(value = "image") MultipartFile image,
                                      @ModelAttribute final ProductCreateRequest productRequest)
        throws IOException {

        this.productService.createProduct(image, productRequest);

        return new ModelAndView("redirect:" + DEFAULT_PRODUCT_URI + "/index");
    }

    /**
     * 상품 생성 페이지 조회를 위한 GetMapping 을 지원 합니다.
     *
     * @return - 상품 생성 폼을 리턴합니다.
     * @since 1.0.0
     */
    @GetMapping("/create")
    public ModelAndView createProduct() {
        ModelAndView mav = new ModelAndView("products/product-create-form");

        List<CategoryRetrieveResponse> categories = this.categoryService.retrieveCategories();
        mav.addObject("categories", categories);

        List<LabelRetrieveResponse> labels = this.labelService.retrieveLabels();
        mav.addObject("labels", labels);

        return mav;
    }

    /**
     * 모든 상품을 조회를 위한 GetMapping 을 지원 합니다.
     * 타임리프에서 products로 조회할 수 있습니다.
     * List 타입 입니다.
     *
     * @return - 모든 상품 조회 페이지를 리턴 합니다.
     * @since 1.0.0
     */
    @GetMapping
    public ModelAndView retrieveProducts() {
        List<ProductResponse> products = this.productService.retrieveProducts();

        ModelAndView mav = new ModelAndView("products/retrieve-products");
        mav.addObject("products", products);

        return mav;
    }

    /**
     * 상품 상세 조회를 위한 GetMapping 을 지원 합니다.
     * 타임리프에서 productDetails로 조회할 수 있습니다.
     *
     * @param id - 상품의 PK 입니다.
     * @return - 상품 상세 페이지를 리턴합니다.
     * @since 1.0.0
     */
    @GetMapping("/{id}")
    public ModelAndView retrieveProductDetails(@PathVariable final Long id) {

        ProductResponse productDetails = this.productService.retrieveProductDetails(id);
        ModelAndView mav = new ModelAndView("products/product-details");
        mav.addObject("productDetails", productDetails);

        return mav;
    }

    /**
     * 카테고리로 상품을 조회하기 위한 GetMapping을 지원 합니다.
     * 타임리프에서 products로 조회할 수 있습니다.
     *
     * @param categorizationCode - 카테고리 대분류 입니다. ex) 100 - 상품
     * @param categoryCode       - 카테고리 소분류 입니다. ex) 101 - 채소
     * @return - 해당 카테고리를 가진 상품 목록 페이지를 리턴합니다.
     * @since 1.0.0
     */
    @GetMapping("/{categorizationCode}/{categoryCode}")
    public ModelAndView retrieveProductsByCategory(@PathVariable final String categorizationCode,
                                                   @PathVariable final String categoryCode) {

        List<ProductResponse> products =
            this.productService.retrieveProductsByCategory(categorizationCode, categoryCode);

        ModelAndView mav = new ModelAndView("products/retrieve-products");
        mav.addObject("products", products);

        return mav;
    }

    /**
     * 상품 수정 페이지로 가기 위한 GetMapping 을 지원합니다.
     * 상품의 원래 속성이 기본으로 지정되어 있습니다.
     *
     * @param id - 상품 PK 입니다.
     * @return - 상품 수정 페이지를 리턴합니다.
     * @since 1.0.0
     */
    @GetMapping("/update/{id}")
    public ModelAndView updateProduct(@PathVariable final Long id) {

        ModelAndView mav = new ModelAndView("products/product-modify-form");

        ProductResponse product = this.productService.retrieveProductDetails(id);
        List<CategoryRetrieveResponse> categories = this.categoryService.retrieveCategories();
        mav.addObject("product", product);
        mav.addObject("categories", categories);
        return mav;
    }

    /**
     * 상품 수정을 위한 PostMapping 을 지원 합니다.
     * multipartFile 의 경우 html form 에서 PUT 맵핑을 적용 시키기 어려워서 일단 POST로 구현했습니다.
     * 차후에 PutMapping으로 수정해야 합니다.
     *
     * @param image          - MultipartFile 타입입니다.
     * @param productRequest - 상품 수정을 위한 DTO 입니다.
     * @param id             - 상품의 PK 입니다.
     * @return - index 페이지를 리턴합니다.
     * @throws IOException 파일 입출력에서 발생하는 에러입니다.
     * @since 1.0.0
     */

    @PostMapping("/update/{id}")
    public ModelAndView updateProduct(@PathVariable final Long id,
                                      @RequestPart(value = "image") final MultipartFile image,
                                      @ModelAttribute final ProductModifyRequest productRequest)
        throws IOException {

        this.productService.updateProduct(id, image, productRequest);

        return new ModelAndView("redirect:" + DEFAULT_PRODUCT_URI + "/index");
    }

    /**
     * 상품 삭제를 위한 PostMapping을 지원합니다.
     * 소프트 삭제입니다.
     *
     * @param productId - 상품의 PK 입니다.
     * @return - index 페이지를 리턴합니다.
     */
    @PostMapping("/{productId}/delete")
    public ModelAndView deleteProduct(@PathVariable final Long productId) {
        this.productService.deleteProduct(productId);

        return new ModelAndView("redirect:" + DEFAULT_PRODUCT_URI + "/index");
    }

}
