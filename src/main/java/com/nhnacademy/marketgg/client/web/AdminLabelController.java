package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.LabelRegisterRequest;
import com.nhnacademy.marketgg.client.dto.LabelRetrieveResponse;
import com.nhnacademy.marketgg.client.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping
    ModelAndView createLabel(@ModelAttribute final LabelRegisterRequest labelRequest)
            throws JsonProcessingException {

        ModelAndView mv = new ModelAndView("redirect:/admin/v1/labels/index");
        labelService.createLabel(labelRequest);

        return mv;
    }

    @GetMapping
    ModelAndView retrieveLabels() {
        ModelAndView mv = new ModelAndView("/labels/index");
        List<LabelRetrieveResponse> responses = labelService.retrieveLabels();

        mv.addObject("labels", responses);

        return mv;
    }

    @DeleteMapping("/{labelId}")
    ModelAndView deleteLabel(@PathVariable final Long labelId) {
        ModelAndView mv = new ModelAndView("redirect:/admin/v1/labels/index");
        labelService.deleteLabel(labelId);

        return mv;
    }

}
