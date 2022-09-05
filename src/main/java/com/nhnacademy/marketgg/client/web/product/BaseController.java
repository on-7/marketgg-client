package com.nhnacademy.marketgg.client.web.product;

import com.nhnacademy.marketgg.client.dto.category.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BaseController implements Controller {

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute(name = "categories")
    public List<CategoryRetrieveResponse> retrieveCategories() {
        return categoryService.retrieveCategories();
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }
}
