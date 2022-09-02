package com.nhnacademy.marketgg.client.web.member;

import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.point.PointRetrieveResponse;
import com.nhnacademy.marketgg.client.paging.Pagination;
import com.nhnacademy.marketgg.client.service.point.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/points")
    public ModelAndView index(@RequestParam(defaultValue = "1") final int page) {
        PageResult<PointRetrieveResponse> listPageResult = this.pointService.retrievePointHistories(page);

        List<PointRetrieveResponse> points = listPageResult.getData();
        Pagination pagination = new Pagination(listPageResult.getTotalPages(), page);

        ModelAndView mav = new ModelAndView("pages/mygg/points/index");
        mav.addObject("points", points);
        mav.addObject("pages", pagination);

        return mav;
    }

}
