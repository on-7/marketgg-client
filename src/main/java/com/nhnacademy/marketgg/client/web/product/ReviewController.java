package com.nhnacademy.marketgg.client.web.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.request.ReviewCreateRequest;
import com.nhnacademy.marketgg.client.service.ReviewService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/products/{productId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{reviewId}")
    public ModelAndView createReview(@PathVariable final Long productId,
                                     @PathVariable final Long reviewId,
                                     final MemberInfo memberInfo,
                                     @ModelAttribute @Valid final ReviewCreateRequest reviewRequest,
                                     BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:" + "/products/" + productId);
        }

        reviewService.createReview(productId, reviewId, memberInfo, reviewRequest);

        return new ModelAndView("redirect:" + "/products/" + productId);
    }
}
