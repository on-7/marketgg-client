package com.nhnacademy.marketgg.client.dto;

import lombok.Getter;

/**
 * 팝업창을 띄울 때 보여줄 메세지와 이후 이동할 페이지를 받는 객체입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@Getter
public class Alert {

    private final String message;
    private final String href;

    public Alert(String message, String href) {
        this.message = message;
        this.href = href;
    }

}
