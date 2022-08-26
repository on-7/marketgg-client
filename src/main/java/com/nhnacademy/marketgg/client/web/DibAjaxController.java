package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.dto.response.DibRetrieveResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.service.DibService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 찜의 비동기 처리를 위한 Controller 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@Controller
@RequestMapping("/dibs")
@RequiredArgsConstructor
public class DibAjaxController {

    private final DibService dibService;
    private static final Integer SUCCESS = 1;

    /**
     * 지정한 상품을 지정한 회원의 찜 목록에 존재하는지 판단하는 Mapping 을 지원합니다.
     *
     * @param productId - 지정한 상품의 식별번호입니다.
     * @return 찜 목록에 상품이 존재하면 1, 없으면 0을 반환합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
    @ResponseBody
    public Map<String, Object> dibView(final Long productId) throws UnAuthenticException, UnAuthorizationException {

        List<DibRetrieveResponse> list = dibService.retrieveDibs();
        Map<String, Object> map = new HashMap<>();

        map.put("exist", 0);

        for (DibRetrieveResponse response : list) {
            if (Objects.equals(response.getProductNo(), productId)) {
                map.put("exist", 1);
            }
        }

        return map;
    }

    /**
     * 지정한 사용자의 찜 목록에 해당 상품을 추가하는 Mapping 을 지원합니다.
     *
     * @param productId - 지정한 상품의 식별번호입니다.
     * @return 찜 목록에 해당 상품을 추가하는 메소드 실행 후 정상적인 처리가 됐다는 1을 반환합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @GetMapping("/insert/{productId}")
    @ResponseBody
    public Integer dibInsert(@PathVariable final Long productId) throws UnAuthenticException, UnAuthorizationException {

        dibService.createDib(productId);

        return SUCCESS;
    }

    /**
     * 지정한 사용자의 찜 목록에서 해당 상품을 제거하는 Mapping 을 지원합니다.
     *
     * @param productId - 지정한 상품의 식별번호입니다.
     * @return 찜 목록에 해당 상품을 제거하는 메소드 실행 후 정상적인 처리가 됐다는 1을 반환합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @GetMapping("/delete/{productId}")
    @ResponseBody
    public Integer dibDelete(@PathVariable final Long productId) throws UnAuthenticException, UnAuthorizationException {

        dibService.deleteDib(productId);

        return SUCCESS;
    }

}
