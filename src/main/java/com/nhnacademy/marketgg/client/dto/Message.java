package com.nhnacademy.marketgg.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Message {

    private String error;
    private String href;

    public Message(String error, String href) {
        this.error = error;
        this.href = href;
    }

}
