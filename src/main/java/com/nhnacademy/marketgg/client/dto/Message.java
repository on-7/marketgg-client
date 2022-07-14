package com.nhnacademy.marketgg.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Message {
    private String message = "";
    private String href = "";

    public Message(String message, String href) {
        this.message = message;
        this.href = href;
    }

}
