package com.nhnacademy.marketgg.client.web.error;

import java.util.Objects;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 예외 발생 시 리디렉션(redirection) 할 페이지를 처리하는 클래스입니다.
 *
 * @author 이제훈
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Controller
public class ExceptionHandlingController implements ErrorController {

    private static final String ERROR_DEFAULT_PAGE_PATH = "pages/errors/default-error";
    private static final String FORBIDDEN_PAGE_PATH = "pages/errors/403";
    private static final String NOT_FOUND_PAGE_PATH = "pages/errors/404";
    private static final String ERROR_5XX_PAGE_PATH = "pages/errors/5xx";

    /**
     * 에러 처리를 담당합니다.
     *
     * @param request - 오류가 발생한 요청 정보가 담긴 {@link HttpServletRequest}
     * @return 에러 페이지
     */
    @GetMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        Object errorStatusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (Objects.nonNull(errorStatusCode)) {
            int statusCode = Integer.parseInt(errorStatusCode.toString());

            if (Objects.equals(statusCode, HttpStatus.FORBIDDEN.value())) {
                return new ModelAndView(FORBIDDEN_PAGE_PATH);
            } else if (Objects.equals(statusCode, HttpStatus.NOT_FOUND.value())) {
                return new ModelAndView(NOT_FOUND_PAGE_PATH);
            } else if (Objects.equals(statusCode, HttpStatus.INTERNAL_SERVER_ERROR.value())) {
                return new ModelAndView(ERROR_5XX_PAGE_PATH);
            }
        }

        return new ModelAndView(ERROR_DEFAULT_PAGE_PATH);
    }

}
