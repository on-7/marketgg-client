package com.nhnacademy.marketgg.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 팝업창을 띄울 때 보여줄 메세지와 이후 이동할 페이지를 받는 객체입니다.
 *
 * @version 1.0.0
 */
@NoArgsConstructor
@Getter
public class Alert {

    private String message;
    private String href;

    public Alert(String message, String href) {
        this.message = message;
        this.href = href;
    }

}
