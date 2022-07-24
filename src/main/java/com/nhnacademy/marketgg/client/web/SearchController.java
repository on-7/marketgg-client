package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.SearchRequest;
import com.nhnacademy.marketgg.client.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/shop/v1/products")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/categories/{categoryCode}/search")
    public ModelAndView searchForCategory(@PathVariable String categoryCode,
                                          @RequestParam Integer page,
                                          @RequestParam Integer size)
            throws ParseException, JsonProcessingException {

        ModelAndView mav = new ModelAndView();
        mav.addObject("response", searchService.searchForCategory(
                new SearchRequest(categoryCode, PageRequest.of(page, size))));

        return mav;
    }

    @GetMapping("/search")
    public ModelAndView searchForKeyword(@RequestParam String keyword,
                                         @RequestParam Integer page,
                                         @RequestParam Integer size)
            throws ParseException, JsonProcessingException {

        ModelAndView mav = new ModelAndView();
        mav.addObject("response", searchService.searchForKeyword(
                new SearchRequest(keyword, PageRequest.of(page, size))));

        return mav;
    }

}
