package com.nhnacademy.marketgg.client.web.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.exception.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 전역 예외 처리 클래스입니다.
 *
 * @version 1.0.0
 */
@Slf4j
@Order
@ControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * JSON 처리 시 발생하는 예외를 처리합니다.
     *
     * @param e - 발생한 예외
     * @return 예외 처리 페이지
     */
    @ExceptionHandler({
        ClientException.class,
        JsonProcessingException.class
    })
    public ModelAndView serverErrorHandle(Exception e) {
        log.error(e.toString());
        // TODO: Error 페이지 제작
        return new ModelAndView("");
    }

    /**
     * 기타 발생할 수 있는 예외를 처리합니다.
     *
     * @param t - 기타 예외
     * @return 예외 처리 페이지
     */
    @ExceptionHandler({
        Throwable.class
    })
    public ModelAndView serverErrorHandle(Throwable t) {
        log.error(t.toString());
        // TODO: Error 페이지 제작
        return new ModelAndView("");
    }

}
