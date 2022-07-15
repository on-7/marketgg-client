package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.LabelRegisterRequest;
import com.nhnacademy.marketgg.client.dto.response.LabelRetrieveResponse;
import com.nhnacademy.marketgg.client.service.LabelService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/shop/v1/admin/labels")
@RequiredArgsConstructor
public class AdminLabelController {

    private final LabelService labelService;

    @PostMapping
    public ModelAndView createLabel(@ModelAttribute final LabelRegisterRequest labelRequest)
            throws JsonProcessingException {

        labelService.createLabel(labelRequest);

        return new ModelAndView("redirect:/admin/v1/labels");
    }

    @GetMapping
    public ModelAndView retrieveLabels() {
        List<LabelRetrieveResponse> responses = labelService.retrieveLabels();

        ModelAndView mav = new ModelAndView("/labels/index");
        mav.addObject("labels", responses);

        return mav;
    }

    @DeleteMapping("/{labelId}")
    public ModelAndView deleteLabel(@PathVariable final Long labelId) {
        labelService.deleteLabel(labelId);

        return new ModelAndView("redirect:/admin/v1/labels/index");
    }

}
