package com.nhnacademy.marketgg.client.web.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.product.ProductCreateRequest;
import com.nhnacademy.marketgg.client.dto.product.ProductInquiryReplyRequest;
import com.nhnacademy.marketgg.client.dto.product.ProductUpdateRequest;
import com.nhnacademy.marketgg.client.dto.category.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.label.LabelRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.product.ProductInquiryResponse;
import com.nhnacademy.marketgg.client.dto.product.ProductResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.paging.Pagination;
import com.nhnacademy.marketgg.client.service.category.CategoryService;
import com.nhnacademy.marketgg.client.service.label.LabelService;
import com.nhnacademy.marketgg.client.service.product.ProductInquiryService;
import com.nhnacademy.marketgg.client.service.product.ProductService;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 상품 컨트롤러 입니다.
 *
 * @version 1.0.0
 */
@Slf4j
@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final LabelService labelService;
    private final ProductInquiryService inquiryService;

    private static final String DEFAULT_PRODUCT_VIEW = "pages/products/product-view";
    private static final String REDIRECT_PRODUCT_URI = "redirect:/admin/products/index";

    /**
     * 기본 상품 index 페이지 GetMapping을 지원합니다.
     *
     * @return - 기본 뷰 페이지를 리턴합니다.
     * @since 1.0.0
     */
    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("pages/products/index");
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
    @PostMapping
    public ModelAndView createProduct(@RequestPart(value = "image") final MultipartFile image,
                                      @ModelAttribute @Valid final ProductCreateRequest productRequest,
                                      BindingResult bindingResult)
        throws IOException {

        if (bindingResult.hasErrors()) {
            log.warn(String.valueOf(bindingResult.getAllErrors().get(0)));
            return new ModelAndView(DEFAULT_PRODUCT_VIEW);
        }

        this.productService.createProduct(image, productRequest);

        return new ModelAndView(REDIRECT_PRODUCT_URI);
    }

    /**
     * 상품 생성 페이지 조회를 위한 GetMapping 을 지원 합니다.
     *
     * @return - 상품 생성 폼을 리턴합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @GetMapping("/create")
    public ModelAndView createProduct() throws UnAuthenticException, UnAuthorizationException {
        ModelAndView mav = new ModelAndView("pages/products/product-create-form");

        List<CategoryRetrieveResponse> categories = this.categoryService.retrieveCategories();
        mav.addObject("categories", categories);

        List<LabelRetrieveResponse> labels = this.labelService.retrieveLabels();
        mav.addObject("labels", labels);

        return mav;
    }

    /**
     * 상품 수정 페이지로 가기 위한 GetMapping 을 지원합니다.
     * 상품의 원래 속성이 기본으로 지정되어 있습니다.
     *
     * @param productId - 상품 PK 입니다.
     * @return - 상품 수정 페이지를 리턴합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @GetMapping("/update/{productId}")
    public ModelAndView updateProduct(@PathVariable final Long productId)
        throws UnAuthenticException, UnAuthorizationException {

        ModelAndView mav = new ModelAndView("pages/products/product-modify-form");

        ProductResponse product = this.productService.retrieveProductDetails(productId);
        List<CategoryRetrieveResponse> categories = this.categoryService.retrieveCategories();
        mav.addObject("categories", categories);

        List<LabelRetrieveResponse> labels = this.labelService.retrieveLabels();
        mav.addObject("labels", labels);
        mav.addObject("product", product);
        mav.addObject("categories", categories);
        return mav;
    }

    /**
     * 상품 수정을 위한 PutMapping 을 지원 합니다.
     *
     * @param image          - MultipartFile 타입입니다.
     * @param productRequest - 상품 수정을 위한 DTO 입니다.
     * @param productId      - 상품의 PK 입니다.
     * @return - index 페이지를 리턴합니다.
     * @throws IOException 파일 입출력에서 발생하는 에러입니다.
     * @since 1.0.0
     */

    @PutMapping("/{productId}")
    public ModelAndView updateProduct(@PathVariable final Long productId,
                                      @RequestPart(value = "image") final MultipartFile image,
                                      @ModelAttribute @Valid final ProductUpdateRequest productRequest,
                                      BindingResult bindingResult)
        throws IOException {

        if (bindingResult.hasErrors()) {
            log.info(bindingResult.getAllErrors().get(0).toString());
            return new ModelAndView(DEFAULT_PRODUCT_VIEW);
        }

        this.productService.updateProduct(productId, image, productRequest);

        return new ModelAndView(REDIRECT_PRODUCT_URI);
    }

    /**
     * 상품 삭제를 위한 DeleteMapping을 지원합니다.
     * 소프트 삭제입니다.
     *
     * @param productId - 상품의 PK 입니다.
     * @return - index 페이지를 리턴합니다.
     */
    @DeleteMapping("/{productId}")
    public ModelAndView deleteProduct(@PathVariable final Long productId) {
        this.productService.deleteProduct(productId);

        return new ModelAndView(REDIRECT_PRODUCT_URI);
    }

    /**
     * 관리자가 모든 상품에 대한 문의를 조회합니다.
     *
     * @return 조회한 상품 문의를 담은 페이지를 반환합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    @GetMapping("/inquiries")
    public ModelAndView retrieveProductInquiry(@RequestParam(defaultValue = "1") final Integer page)
        throws UnAuthenticException, UnAuthorizationException {

        PageResult<ProductInquiryResponse> inquiries = this.inquiryService.retrieveInquiries(page);
        Pagination pagination = new Pagination(inquiries.getTotalPages(), page);

        ModelAndView mav = new ModelAndView("pages/admin/products/inquiries");
        mav.addObject("inquiries", inquiries.getData());
        mav.addObject("pages", pagination);

        return mav;
    }

    /**
     * 관리자가 상품 문의를 삭제하는 DeleteMapping 을 지원합니다.
     *
     * @param productId - 삭제할 상품 문의의 상품 Id 입니다.
     * @param inquiryId - 삭제할 상품 문의 Id 입니다.
     * @return 상품 문의 조회 페이지를 리턴합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    @DeleteMapping("/{productId}/inquiries/{inquiryId}")
    public ModelAndView deleteProductInquiryByAdmin(@PathVariable final Long productId,
                                                    @PathVariable final Long inquiryId)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        this.inquiryService.deleteProductInquiry(productId, inquiryId);
        return new ModelAndView("redirect:/admin/products/inquiries");
    }

    /**
     * 관리자가 상품 문의에 답글을 남기는 PutMapping 을 지원합니다.
     *
     * @param replyRequest - 답글을 남길 상품 문의 정보와 답글이 들어있는 DTO 입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    @PutMapping("/{productId}/inquiries/{inquiryId}")
    @ResponseBody
    public ModelAndView replyInquiry(@PathVariable final Long productId,
                                     @PathVariable final Long inquiryId,
                                     @ModelAttribute @Valid final ProductInquiryReplyRequest replyRequest)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        this.inquiryService.updateReply(replyRequest, inquiryId);

        return new ModelAndView("redirect:/admin/products/inquiries");
    }

}
