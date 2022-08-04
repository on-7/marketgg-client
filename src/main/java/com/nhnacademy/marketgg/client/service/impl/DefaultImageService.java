package com.nhnacademy.marketgg.client.service.impl;

import com.nhnacademy.marketgg.client.repository.ImageRepository;
import com.nhnacademy.marketgg.client.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultImageService implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public byte[] retrieveImage(Long id) {
        return imageRepository.retrieveImage(id);
    }

}
