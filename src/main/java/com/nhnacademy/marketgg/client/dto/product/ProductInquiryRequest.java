package com.nhnacademy.marketgg.client.dto.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductInquiryRequest {

    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(max = 50, message = "제목의 최대 글자 수는 50자입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력값입니다.")
    @Size(max = 200, message = "내용의 최대 글자 수는 200자입니다.")
    private String content;

    @NotNull
    private Boolean isSecret;

}
