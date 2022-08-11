package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.ProductInquiryRequest;
import com.nhnacademy.marketgg.client.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.service.ProductInquiryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductInquiryController {

    private final ProductInquiryService inquiryService;

    @GetMapping("/{productId}/inquiry")
    public ModelAndView createProductInquiry(@PathVariable final Long productId) {
        ModelAndView mav = new ModelAndView("pages/products/product-inquiry-form");
        mav.addObject("productId", productId);

        return mav;
    }

    @PostMapping("/{productId}/inquiry")
    public ModelAndView createProductInquiry(@PathVariable final Long productId,
                                             @ModelAttribute final
                                             ProductInquiryRequest inquiryRequest)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        this.inquiryService.createInquiry(productId, inquiryRequest);
        return new ModelAndView("redirect:/products/" + productId + "/inquiries");
    }

    @GetMapping("/{productId}/inquiries")
    public ModelAndView retrieveProductInquiry(@PathVariable final Long productId)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        List<ProductInquiryResponse> inquiries =
            this.inquiryService.retrieveInquiryByProduct(productId);

        ModelAndView mav = new ModelAndView("pages/products/product-inquiry");
        mav.addObject("inquiries", inquiries);

        return mav;
    }

    @GetMapping("/{productId}/inquiry/{inquiryId}")
    public ModelAndView deleteProductInquiry(@PathVariable final Long productId,
                                             @PathVariable final Long inquiryId)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        this.inquiryService.deleteProductInquiry(productId, inquiryId);
        return new ModelAndView("redirect:/products/" + productId + "/inquiries");
    }

}
