package com.nhnacademy.marketgg.client.dto.response;

import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class RestResponsePage<T> extends PageImpl<T> {

    public RestResponsePage(final List<T> content, final Pageable pageable,
                            final long total) {
        super(content, pageable, total);
    }

}
