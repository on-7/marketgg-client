package com.nhnacademy.marketgg.client.dto.elastic.request.product;

import com.nhnacademy.marketgg.client.dto.elastic.request.product.sortdata.Price;
import com.nhnacademy.marketgg.client.dto.elastic.request.product.sortdata.Id;
import com.nhnacademy.marketgg.client.dto.elastic.request.product.sortdata.Score;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SortForPrice {

    private Score _score;

    private Price price;

    private Id id;

}
