package com.nhnacademy.marketgg.client.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductInquiryReplyRequest {

    @NotNull
    private Long productId;

    @NotNull
    private Long inquiryId;

    @NotNull
    @Size(min = 10, max = 300, message = "문의 답변 내용은 10자 이상, 300자 이하만 가능합니다.")
    private String adminReply;

}
