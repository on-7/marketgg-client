package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.annotation.RoleCheck;
import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.request.ReviewCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ReviewUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.ReviewResponse;
import com.nhnacademy.marketgg.client.service.ReviewService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/products/{productId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @RoleCheck
    @PostMapping
    public ModelAndView createReview(@PathVariable final Long productId,
                                     final MemberInfo memberInfo,
                                     @ModelAttribute @Valid final ReviewCreateRequest reviewRequest,
                                     BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/" + "products/product-view");
        }

        reviewService.createReview(productId, memberInfo, reviewRequest);

        return new ModelAndView("redirect:/" + "products/product-view");
    }

    @GetMapping
    public ModelAndView retrieveReviews(@PathVariable final Long productId) {

        List<ReviewResponse> reviewResponses = reviewService.retrieveReviews(productId);

        ModelAndView mav = new ModelAndView("products/reviews/review-view");
        mav.addObject("reviews", reviewResponses);

        return mav;
    }

    @GetMapping("/{reviewId}")
    public ModelAndView retrieveReview(@PathVariable final Long productId,
                                       @PathVariable final Long reviewId,
                                       final MemberInfo memberInfo) {

        ReviewResponse reviewResponse = reviewService.retrieveReview(productId, reviewId, memberInfo);

        ModelAndView mav = new ModelAndView("products/reviews/review-view");
        mav.addObject("reviewDetail", reviewResponse);

        return mav;
    }

    @RoleCheck
    @PutMapping("/{reviewId}")
    public ModelAndView updateReview(@PathVariable final Long productId, @PathVariable final Long reviewId,
                                     final MemberInfo memberInfo,
                                     @ModelAttribute @Valid final ReviewUpdateRequest reviewRequest,
                                     BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("products/product-view");
        }

        reviewService.updateReview(productId, reviewId, memberInfo, reviewRequest);

        return new ModelAndView("redirect:/" + "products/product-view");
    }

    @RoleCheck
    @DeleteMapping("/{reviewId}")
    public ModelAndView deleteReview(@PathVariable final Long productId, @PathVariable final Long reviewId,
                                     final MemberInfo memberInfo) {

        reviewService.deleteReview(productId, reviewId, memberInfo);

        return new ModelAndView("redirect:/" + "products/product-view");
    }

}
