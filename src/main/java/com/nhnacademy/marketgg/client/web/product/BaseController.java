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
import java.util.stream.Collectors;

/**
 * 중복되는 카테고리 조회 코드를 분리하기 위한 컨트롤러입니다.
 *
 * @author 조현진
 */
public class BaseController implements Controller {

    @Autowired
    private CategoryService categoryService;

    /**
     * 상품에 관한 카테고리 조회 정보만을 조회합니다.
     * @return - 상품 카테고리 조회 정보를 반환합니다.
     */
    @ModelAttribute(name = "categories")
    public List<CategoryRetrieveResponse> retrieveCategories() {
        return categoryService.retrieveCategoriesOnlyProducts();
    }

    /**
     * 상품 검색에 관한 카테고리 정보를 조회
     *
     * @return - 검색에 필요한 상품 카테고리 목록을 반환
     * @since 1.0.0
     */
    @ModelAttribute(name = "categoryList")
    public List<CategoryRetrieveResponse> retrieveResponses() {
        return categoryService.retrieveCategoriesOnlyProducts()
                .stream()
                .filter(o -> o.getCategoryCode().length() == 6)
                .collect(Collectors.toList());
    }

    /**
     * Controller를 implements하여 생긴 메소드입니다.
     * 아무 기능이 없습니다.
     */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }

}
