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

@Controller
@RequestMapping("/dibs")
@RequiredArgsConstructor
public class DibAjaxController {

    private final DibService dibService;

    /**
     * 지정한 상품을 지정한 회원의 찜 목록에 추가하는 Mapping 을 지원합니다.
     *
     * @param memberId  - 지정한 회원의 식별변호입니다.
     * @param productId - 지정한 상품의 식별번호입니다.
     * @return 다시 상품 조회 페이지로 이동합니다.
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

    @GetMapping("/insert/{memberId}/{productId}")
    public Integer dibInsert(@PathVariable final Long memberId, @PathVariable final Long productId) {

        dibService.createDib(memberId, productId);

        return 1;
    }

    @GetMapping("/delete/{memberId}/{productId}")
    public Integer dibDelete(@PathVariable final Long memberId, @PathVariable final Long productId) {

        dibService.deleteDib(memberId, productId);

        return 1;
    }

}
