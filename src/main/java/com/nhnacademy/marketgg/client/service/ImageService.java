package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.dto.response.ImageResponse;

public interface ImageService {

    ImageResponse retrieveImage(final Long id);

    String downloadImage(final String url);
}
