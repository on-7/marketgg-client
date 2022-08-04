package com.nhnacademy.marketgg.client.web.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.exception.ClientException;
import com.nhnacademy.marketgg.client.exception.NotFoundException;
import com.nhnacademy.marketgg.client.exception.SecureManagerException;
import com.nhnacademy.marketgg.client.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 전역 예외 처리 클래스입니다.
 */
@Slf4j
@Order
@ControllerAdvice
public class GlobalControllerAdvice {

    /**
     * Http status 4xx 예외를 처리합니다.
     *
     * @param e - 4xx 예외
     * @return - 에러 페이지
     */
    @ExceptionHandler(ClientException.class)
    public ModelAndView clientException(ClientException e) {
        log.error("", e);
        // TODO: Error 페이지 제작
        return new ModelAndView();
    }

    /**
     * Http status 5xx 예외를 처리합니다.
     *
     * @param e - 5xx 예외
     * @return - 에러 페이지
     */
    @ExceptionHandler({
        ServerException.class,
        SecureManagerException.class,
        JsonProcessingException.class
    })
    public ModelAndView serverException(ServerException e) {
        log.error("", e);
        // TODO: Error 페이지 제작
        return new ModelAndView();
    }

    /**
     * Http status 404 예외를 처리합니다.
     *
     * @param e - 404 예외
     * @return - 404 Not Found
     */
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView notFoundException(NotFoundException e) {
        log.error(e.toString());
        // TODO: Error 페이지 제작
        return new ModelAndView();
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
        log.error("", t);
        // TODO: Error 페이지 제작
        return new ModelAndView("");
    }

}
