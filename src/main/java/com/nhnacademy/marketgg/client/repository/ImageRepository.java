package com.nhnacademy.marketgg.client.repository;

import com.nhnacademy.marketgg.client.dto.response.ImageResponse;

public interface ImageRepository {

    ImageResponse retrieveImage(final Long id);

    String downloadImage(final String url);
}
