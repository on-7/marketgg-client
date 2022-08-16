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
import org.springframework.web.servlet.ModelAndView;

/**
 * 후기를 관리하기 위한 controller입니다.
 *
 * @author 조현진
 */
@Slf4j
@Controller
@RequestMapping("/products/{productId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    private static final String DEFAULT_PRODUCT_DETAIL_VIEW = "products/product-view";
    private static final String REDIRECT_PRODUCT_DETAIL_VIEW = "redirect:/products/product-view";

    /**
     * 후기를 등록합니다.
     * 권한이 있는 사용자만 후기를 등록할 수 있습니다.
     *
     * @param productId     - 후기가 달릴 상품의 번호입니다.
     * @param memberInfo    - 권한 체크를 위한 사용자 정보입니다.
     * @param reviewRequest - 등록될 후기의 정보를 담고있습니다.
     * @param bindingResult - validation 체크를 합니다.
     * @return - 해당 상품의 view를 반환합니다.
     * @throws JsonProcessingException - Json expeption을 던집니다.
     */

    @RoleCheck
    @PostMapping
    public ModelAndView createReview(@PathVariable final Long productId,
                                     final MemberInfo memberInfo,
                                     @ModelAttribute @Valid final ReviewCreateRequest reviewRequest,
                                     BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors()) {
            log.info(bindingResult.getAllErrors().get(0).toString());
            return new ModelAndView(REDIRECT_PRODUCT_DETAIL_VIEW);
        }

        reviewService.createReview(productId, memberInfo, reviewRequest);

        return new ModelAndView(REDIRECT_PRODUCT_DETAIL_VIEW);
    }

    /**
     * 후기의 전체 목록을 조회합니다.
     *
     * @param productId - 후기가 달린 상품의 상품번호입니다.
     * @return - 후기 목록이 담긴 view를 반환합니다.
     */
    @GetMapping
    public ModelAndView retrieveReviews(@PathVariable final Long productId) {

        List<ReviewResponse> reviewResponses = reviewService.retrieveReviews(productId);

        ModelAndView mav = new ModelAndView("products/reviews/review-view");
        mav.addObject("reviews", reviewResponses);

        return mav;
    }

    /**
     * 선택한 후기를 상세조회합니다.
     *
     * @param productId - 해당 후기가 달린 상품의 상품번호입니다.
     * @param reviewId  - 선택한 후기의 후기 번호입니다.
     * @return - 후기의 상세 정보가 담긴 view를 반환합니다.
     */
    @GetMapping("/{reviewId}")
    public ModelAndView retrieveReview(@PathVariable final Long productId,
                                       @PathVariable final Long reviewId) {

        ReviewResponse reviewResponse = reviewService.retrieveReview(productId, reviewId);

        ModelAndView mav = new ModelAndView("products/reviews/review-view");
        mav.addObject("reviewDetail", reviewResponse);

        return mav;
    }

    /**
     * 선택한 후기를 수정합니다.
     * 권한이 있는 사용자만 수정할 수 있습니다.
     *
     * @param productId     - 후기가 달린 상품의 상품번호입니다.
     * @param reviewId      - 달린 후기의 후기번호입니다.
     * @param memberInfo    - 후기를 수정할 회원의 회원정보입니다.
     * @param reviewRequest - 수정할 후기의 정보다 담겨있습니다.
     * @param bindingResult - validation체크를 합니다.
     * @return - 후기가 달릴 상품의 view를 반환합니다.
     * @throws JsonProcessingException - JsonException을 던집니다.
     */
    @RoleCheck
    @PutMapping("/{reviewId}")
    public ModelAndView updateReview(@PathVariable final Long productId, @PathVariable final Long reviewId,
                                     final MemberInfo memberInfo,
                                     @ModelAttribute @Valid final ReviewUpdateRequest reviewRequest,
                                     BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors()) {
            log.info(bindingResult.getAllErrors().get(0).toString());
            return new ModelAndView(DEFAULT_PRODUCT_DETAIL_VIEW);
        }

        reviewService.updateReview(productId, reviewId, memberInfo, reviewRequest);

        return new ModelAndView(REDIRECT_PRODUCT_DETAIL_VIEW);
    }

    /**
     * 선택한 후기를 삭제합니다.
     *
     * @param productId  - 삭제할 후기가 달린 상품의 상품번호입니다.
     * @param reviewId   - 삭제할 후기의 후기번호입니다.
     * @param memberInfo - 본인이 작성한 후기인지 검증하기 위한 회원의 정보입니다.
     * @return - 해당 상품의 view를 반환합니다.
     */
    @RoleCheck
    @DeleteMapping("/{reviewId}")
    public ModelAndView deleteReview(@PathVariable final Long productId, @PathVariable final Long reviewId,
                                     final MemberInfo memberInfo) {

        reviewService.deleteReview(productId, reviewId, memberInfo);

        return new ModelAndView(REDIRECT_PRODUCT_DETAIL_VIEW);
    }

}
