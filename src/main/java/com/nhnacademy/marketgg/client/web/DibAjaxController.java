package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.dto.response.DibRetrieveResponse;
import com.nhnacademy.marketgg.client.service.DibService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 찜의 비동기 처리를 위한 Controller 입니다.
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/dibs")
@RequiredArgsConstructor
public class DibAjaxController {

    private final DibService dibService;

    /**
     * 지정한 상품을 지정한 회원의 찜 목록에 존재하는지 판단하는 Mapping 을 지원합니다.
     *
     * @param memberId  - 지정한 회원의 식별변호입니다.
     * @param productId - 지정한 상품의 식별번호입니다.
     * @return 찜 목록에 상품이 존재하면 1, 없으면 0을 반환합니다.
     * @since 1.0.0
     */
    @PostMapping(produces = "application/json")
    @ResponseBody
    public Map<String, Object> dibView(final Long memberId, final Long productId) {

        List<DibRetrieveResponse> list = dibService.retrieveDibs(memberId);
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
     * @param memberId  - 지정한 회원의 식별변호입니다.
     * @param productId - 지정한 상품의 식별번호입니다.
     * @return 찜 목록에 해당 상품을 추가하는 메소드 실행 후 정상적인 처리가 됐다는 1을 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/insert/{memberId}/{productId}")
    @ResponseBody
    public int dibInsert(@PathVariable final Long memberId, @PathVariable final Long productId) {

        dibService.createDib(memberId, productId);

        return 1;
    }

    /**
     * 지정한 사용자의 찜 목록에서 해당 상품을 제거하는 Mapping 을 지원합니다.
     *
     * @param memberId  - 지정한 회원의 식별변호입니다.
     * @param productId - 지정한 상품의 식별번호입니다.
     * @return 찜 목록에 해당 상품을 제거하는 메소드 실행 후 정상적인 처리가 됐다는 1을 반환합니다.
     * @since 1.0.0
     */
    @GetMapping("/delete/{memberId}/{productId}")
    @ResponseBody
    public int dibDelete(@PathVariable final Long memberId, @PathVariable final Long productId) {

        dibService.deleteDib(memberId, productId);

        return 1;
    }

}
