package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.LabelRegisterRequest;
import com.nhnacademy.marketgg.client.dto.LabelRetrieveResponse;
import com.nhnacademy.marketgg.client.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/v1/labels")
@RequiredArgsConstructor
public class AdminLabelController {

    private final LabelService labelService;

    @GetMapping("/index")
    ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/labels/index");

        return mv;
    }

    @GetMapping("/create")
    ModelAndView doCreateLabel() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/labels/create-form");

        return mv;
    }

    @PostMapping
    ModelAndView createLabel(@ModelAttribute final LabelRegisterRequest labelRequest)
            throws JsonProcessingException {

        ModelAndView mv = new ModelAndView();
        labelService.createLabel(labelRequest);
        mv.setViewName("redirect:/admin/v1/labels/index");

        return mv;
    }

    @GetMapping("/{labelId}")
    ModelAndView deleteLabel(@PathVariable final Long labelId) {

        ModelAndView mv = new ModelAndView();
        labelService.deleteLabel(labelId);
        mv.setViewName("redirect:/admin/v1/labels/index");

    @GetMapping
    ModelAndView retrieveLabels() {

        ModelAndView mv = new ModelAndView();
        List<LabelRetrieveResponse> responses = labelService.retrieveLabels();

        mv.setViewName("/labels/retrieve");
        mv.addObject("labels", responses);

        return mv;
    }

}
