package com.nhnacademy.marketgg.client.web.advice;

import com.nhnacademy.marketgg.client.exception.LoginFailException;
import com.nhnacademy.marketgg.client.exception.LogoutException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 인증 관련 예외를 처리합니다.
 */
@Slf4j
@Order(100)
@ControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler({
        UnAuthenticException.class
    })
    public ModelAndView unauthenticException(UnAuthenticException e) {
        return new ModelAndView();
    }

    @ExceptionHandler({
        UnAuthorizationException.class
    })
    public ModelAndView unAuthorizationException(UnAuthorizationException e) {
        return new ModelAndView();
    }

    /**
     * 로그아웃 시 발생하는 예외를 처리합니다.
     *
     * @param e - 로그아웃 시 발생한 예외
     * @return - 에러 페이지
     */
    @ExceptionHandler(LogoutException.class)
    public ModelAndView logoutException(LogoutException e) {
        ModelAndView mav = new ModelAndView();

        return mav;
    }

    /**
     * 로그인시 발생한 예외를 처리합니다. (401)
     *
     * @param e - 401 예외
     * @return - 에러 페이지
     */
    @ExceptionHandler(LoginFailException.class)
    public ModelAndView loginFailException(LoginFailException e) {
        ModelAndView mav = new ModelAndView();

        return mav;
    }

}
