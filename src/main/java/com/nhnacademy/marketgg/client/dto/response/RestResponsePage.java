package com.nhnacademy.marketgg.client.dto.response;

import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class RestResponsePage<T> extends PageImpl<T> {

    public RestResponsePage(List<T> content, Pageable pageable,
                            long total) {
        super(content, pageable, total);
    }
}
