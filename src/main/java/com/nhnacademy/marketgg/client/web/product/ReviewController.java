package com.nhnacademy.marketgg.client.web.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.annotation.Auth;
import com.nhnacademy.marketgg.client.dto.common.MemberInfo;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.review.ReviewCreateRequest;
import com.nhnacademy.marketgg.client.dto.review.ReviewUpdateRequest;
import com.nhnacademy.marketgg.client.dto.review.ReviewResponse;
import com.nhnacademy.marketgg.client.service.member.MemberService;
import com.nhnacademy.marketgg.client.service.review.ReviewService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

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
    private final MemberService memberService;

    private static final String DEFAULT_PRODUCT_DETAIL_VIEW = "pages/products/product-view";
    private static final String REDIRECT_PRODUCT_DETAIL_VIEW = "redirect:/products/";

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

    @Auth
    @PostMapping
    public ModelAndView createReview(@PathVariable final Long productId,
                                     final MemberInfo memberInfo,
                                     @ModelAttribute @Valid final ReviewCreateRequest reviewRequest,
                                     BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors()) {
            log.info(bindingResult.getAllErrors().get(0).toString());
            return new ModelAndView(REDIRECT_PRODUCT_DETAIL_VIEW + productId);
        }

        reviewService.createReview(productId, memberInfo, reviewRequest);

        return new ModelAndView(REDIRECT_PRODUCT_DETAIL_VIEW + productId);
    }

    /**
     * 비동기로 전체 후기를 조회하기 위한 컨트롤러입니다.
     *
     * @param id - 상품의 pk 입니다.
     * @return - JSON형식의 후기를 반환합니다.
     */

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PageResult<ReviewResponse>> retrieveReviews(@PathVariable(name = "productId") final Long id,
                                                                      @RequestParam(defaultValue = "0") int page) {
        PageResult<ReviewResponse> reviewResponsePageResult = reviewService.retrieveReviews(id, page);

        return new ResponseEntity<>(reviewResponsePageResult, HttpStatus.OK);
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

        ModelAndView mav = new ModelAndView("pages/products/reviews/review-view");
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
    @Auth
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
    @Auth
    @DeleteMapping("/{reviewId}")
    public ModelAndView deleteReview(@PathVariable final Long productId, @PathVariable final Long reviewId,
                                     final MemberInfo memberInfo, HttpServletResponse response) throws IOException {

        ReviewResponse reviewResponse = reviewService.retrieveReview(productId, reviewId);

        if (!Objects.equals(reviewResponse.getMemberName(), memberInfo.getName())) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('본인이 작성한 댓글만 지울 수 있습니다!'); location.href='/index';</script>");
            out.flush();
            out.close();
        }

        reviewService.deleteReview(productId, reviewId, memberInfo);

        return new ModelAndView(REDIRECT_PRODUCT_DETAIL_VIEW + productId);
    }

}
