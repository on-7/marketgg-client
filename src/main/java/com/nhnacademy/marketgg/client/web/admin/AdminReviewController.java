package com.nhnacademy.marketgg.client.web.admin;

import com.nhnacademy.marketgg.client.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 관리자의 후기 관리를 위한 Controller입니다.
 */
@Controller
@RequestMapping("/admin/products/{productId}/reviews")
@RequiredArgsConstructor
public class AdminReviewController {

    private final ReviewService reviewService;

    private static final String DEFAULT_PRODUCT_URI = "/admin/products";

    /**
     * 선택한 후기를 베스트 후기로 선정합니다.
     *
     * @param productId - 후기가 달린 상품의 상품번호입니다.
     * @param reviewId  - 베스트 후기로 선정할 후기의 후기번호입니다.
     * @return - 해당 후기가 달린 상품의 view를 반환합니다.
     */
    @PostMapping("/{reviewId}/make-best")
    public ModelAndView makeBestReview(@PathVariable final Long productId, @PathVariable final Long reviewId,
                                       HttpServletResponse response) throws IOException {

        reviewService.makeBestReview(productId, reviewId);

        return new ModelAndView("redirect:/products/" + productId);
    }

}
