package com.nhnacademy.marketgg.client.web.member;

import com.nhnacademy.marketgg.client.dto.response.PointRetrieveResponse;
import com.nhnacademy.marketgg.client.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/points/index")
    public ModelAndView index() {
        return new ModelAndView("pages/points/index");
    }

    @GetMapping("/{memberId}/points")
    public ModelAndView retrievePointHistory(@PathVariable final Long memberId) {
        PointRetrieveResponse response = this.pointService.retrievePointHistories(memberId);

        ModelAndView mav = new ModelAndView("pages/points/index");
        mav.addObject("points", response);

        return mav;
    }

}
