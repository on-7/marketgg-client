package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.dto.response.ImageResponse;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    ImageResponse retrieveImage(final Long id);

    String downloadImage(final String url);

    String uploadImage(final MultipartFile image) throws IOException;
}
