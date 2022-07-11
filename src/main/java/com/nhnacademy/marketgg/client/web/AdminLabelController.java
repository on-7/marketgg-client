package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.LabelRegisterRequest;
import com.nhnacademy.marketgg.client.dto.LabelRetrieveResponse;
import com.nhnacademy.marketgg.client.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/v1/labels")
@RequiredArgsConstructor
public class AdminLabelController {

    private final LabelService labelService;

    @GetMapping("/create")
    public ModelAndView doCreateLabel() {
        return new ModelAndView("/labels/create-form");
    }

    @PostMapping
    public ModelAndView
    createLabel(@ModelAttribute final LabelRegisterRequest labelRequest) throws JsonProcessingException {
        labelService.createLabel(labelRequest);

        return new ModelAndView("redirect:/admin/v1/labels/index");
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
