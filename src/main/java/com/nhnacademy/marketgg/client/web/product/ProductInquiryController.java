package com.nhnacademy.marketgg.client.web.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.product.ProductInquiryRequest;
import com.nhnacademy.marketgg.client.dto.product.ProductInquiryResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.paging.Pagination;
import com.nhnacademy.marketgg.client.service.product.ProductInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 상품 문의 Controller 입니다.
 *
 * @author 민아영
 * @version 1.0.0
 */
@Controller
@RequestMapping("/products/{productId}/inquiries")
@RequiredArgsConstructor
public class ProductInquiryController {

    private final ProductInquiryService productInquiryService;

    /**
     * 회원이 상품 문의를 작성할 GetMapping 을 지원합니다.
     *
     * @param productId - 상품 문의를 등록할 상품 Id 입니다.
     * @return 상품 문의 작성 페이지를 반환합니다.
     * @author 민아영
     * @since 1.0.0
     */
    @GetMapping
    public ModelAndView createProductInquiry(@PathVariable final Long productId) {
        ModelAndView mav = new ModelAndView("pages/products/inquiries/product-inquiry-form");
        mav.addObject("productId", productId);

        return mav;
    }

    /**
     * 회원이 상품 문의를 작성하는 PostMapping 을 지원합니다.
     *
     * @param productId      - 상품 문의를 등록할 상품 Id 입니다.
     * @param inquiryRequest - 상품 문의에 작성에 필요한 요청 Dto 입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    @PostMapping
    public ModelAndView createProductInquiry(@PathVariable final Long productId,
                                             @ModelAttribute final
                                             ProductInquiryRequest inquiryRequest)
            throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        this.productInquiryService.createInquiry(productId, inquiryRequest);
        return new ModelAndView("redirect:/products/" + productId);
    }

    /**
     * 상품에 대한 모든 상품 문의를 조회할 GetMapping 을 지원합니다.
     *
     * @param productId - 상품 문의를 등록할 상품 Id 입니다.
     * @return 조회한 상품 문의를 담은 페이지를 반환합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @author 민아영
     * @since 1.0.0
     */
    @GetMapping("/index")
    public ModelAndView retrieveProductInquiry(@RequestParam(defaultValue = "1") final Integer page,
                                               @PathVariable final Long productId)
            throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        PageResult<ProductInquiryResponse> inquiries =
                this.productInquiryService.retrieveInquiryByProduct(page, productId);
        Pagination pagination = new Pagination(inquiries.getTotalPages(), page);

        ModelAndView mav = new ModelAndView("pages/products/inquiries/product-inquiry");
        mav.addObject("inquiries", inquiries.getData());
        mav.addObject("pages", pagination);

        return mav;
    }

    /**
     * 회원이 작성한 상품 문의를 삭제하는 DeleteMapping 을 지원합니다.
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
    @DeleteMapping("/{inquiryId}")
    public ModelAndView deleteProductInquiry(@PathVariable final Long productId,
                                             @PathVariable final Long inquiryId)
            throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        this.productInquiryService.deleteProductInquiry(productId, inquiryId);
        return new ModelAndView("redirect:/members/product-inquiries");
    }

}
