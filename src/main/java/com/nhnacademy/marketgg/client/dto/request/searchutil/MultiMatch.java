package com.nhnacademy.marketgg.client.dto.request.searchutil;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MultiMatch {

    private String query;

    private List<String> fields;

}
