package com.nhnacademy.marketgg.client.service.impl;

import com.nhnacademy.marketgg.client.dto.response.ImageResponse;
import com.nhnacademy.marketgg.client.repository.ImageRepository;
import com.nhnacademy.marketgg.client.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultImageService implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public ImageResponse retrieveImage(Long id) {
        return imageRepository.retrieveImage(id);
    }

    @Override
    public String downloadImage(String url) {
        return imageRepository.downloadImage(url);
    }

}
