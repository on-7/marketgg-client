package com.nhnacademy.marketgg.client.web.cart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.request.ProductToCartRequest;
import com.nhnacademy.marketgg.client.dto.response.CartProductResponse;
import com.nhnacademy.marketgg.client.dto.response.common.CommonResponse;
import com.nhnacademy.marketgg.client.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.service.CartService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 장바구니 관련 요청을 처리합니다.
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * 사용자가 상품을 장바구니에 추가하는 요청을 처리합니다.
     *
     * @param productAddRequest - 장바구니에 담는 상품의 정보입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     */
    // 비동기로 처리 예정
    @PostMapping
    @ResponseBody
    public ResponseEntity<CommonResponse> addToProduct(@RequestBody @Validated ProductToCartRequest productAddRequest)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        cartService.addProduct(productAddRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>(true));
    }

    /**
     * 사용자의 장바구니 조회 요청을 처리합니다.
     *
     * @return - 장바구니에 담긴 상품 목록입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     */
    @GetMapping
    public ModelAndView retrieveCart(MemberInfo memberInfo)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        ModelAndView mav = new ModelAndView("pages/carts/index");
        List<CartProductResponse> carts = cartService.retrieveCarts();
        // List<CartProductResponse> carts = List.of(
        //     new CartProductResponse(1L, "포카칩", "https://console.toast.com/project/5WrZ1g5H/storage/object-storage", 10,
        //         1300L),
        //     new CartProductResponse(2L, "자몽 주스", "https://console.toast.com/project/5WrZ1g5H/storage/object-storage", 9,
        //         2000L),
        //     new CartProductResponse(3L, "오렌지 주스", "https://console.toast.com/project/5WrZ1g5H/storage/object-storage",
        //         8, 1500L),
        //     new CartProductResponse(4L, "수미칩", "https://console.toast.com/project/5WrZ1g5H/storage/object-storage", 7,
        //         1300L),
        //     new CartProductResponse(5L, "김자몽", "https://console.toast.com/project/5WrZ1g5H/storage/object-storage", 6,
        //         2300L),
        //     new CartProductResponse(6L, "델몬트 오렌지", "https://console.toast.com/project/5WrZ1g5H/storage/object-storage",
        //         5, 2100L),
        //     new CartProductResponse(7L, "프링글스", "https://console.toast.com/project/5WrZ1g5H/storage/object-storage", 4,
        //         1000L),
        //     new CartProductResponse(8L, "자몽 주스", "https://console.toast.com/project/5WrZ1g5H/storage/object-storage", 3,
        //         1900L),
        //     new CartProductResponse(9L, "썬키스트 오렌지", "https://console.toast.com/project/5WrZ1g5H/storage/object-storage",
        //         2, 1200L),
        //     new CartProductResponse(10L, "포테토칩", "https://console.toast.com/project/5WrZ1g5H/storage/object-storage", 1,
        //         1500L)
        // );
        mav.addObject("carts", carts);
<<<<<<< HEAD
        
=======
        if (!memberInfo.isNull()) {
            mav.addObject("memberInfo", memberInfo);
        }

>>>>>>> 29a9791 (Refactor: 장바구니 기능 구현 #183)
        return mav;
    }

    /**
     * 사용자의 장바구니에 담긴 상품의 수량을 변경 요청을 처리합니다.
     *
     * @param productUpdateRequest - 장바구니에 담긴 상품 중 수량을 변경하려는 상품의 정보입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     */
    @PatchMapping
    @ResponseBody
    public ResponseEntity<CommonResponse> updateAmount(@RequestBody @Valid ProductToCartRequest productUpdateRequest
        , HttpServletRequest request)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {
        System.out.println(request.getRemoteAddr());
        cartService.updateAmount(productUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>(true));
    }

    /**
     * 장바구니에 담긴 상품 삭제 요청을 처리합니다.
     *
     * @param products - 장바구니에 담긴 상품 중 삭제하려는 상품의 정보입니다.
     * @throws JsonProcessingException  - 응답으로 온 Json 데이터를 역직렬화 시 발생하는 예외입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     */
    // 비동기로 처리 예정
    @DeleteMapping
    @ResponseBody
    public ResponseEntity<CommonResponse> deleteProduct(@RequestBody List<Long> products)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        cartService.deleteProducts(products);

        return ResponseEntity.status(HttpStatus.OK)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(new SingleResponse<>(true));
    }

}
