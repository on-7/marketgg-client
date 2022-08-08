package com.nhnacademy.marketgg.client.service.impl;

import com.nhnacademy.marketgg.client.dto.response.ImageResponse;
import com.nhnacademy.marketgg.client.repository.ImageRepository;
import com.nhnacademy.marketgg.client.service.ImageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DefaultImageService implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public ImageResponse retrieveImage(final Long id) {
        return imageRepository.retrieveImage(id);
    }

    @Override
    public String downloadImage(final String url) {
        return imageRepository.downloadImage(url);
    }

    @Override
    public String uploadImage(final MultipartFile image) throws IOException {
        return imageRepository.uploadImage(image);
    }

}
