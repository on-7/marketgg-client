package com.nhnacademy.marketgg.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

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
