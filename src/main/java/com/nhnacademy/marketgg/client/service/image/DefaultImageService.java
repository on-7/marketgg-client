package com.nhnacademy.marketgg.client.service.image;

import com.nhnacademy.marketgg.client.dto.image.ImageResponse;
import com.nhnacademy.marketgg.client.repository.image.ImageRepository;
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
