package com.nhnacademy.marketgg.client.repository.image;

import com.nhnacademy.marketgg.client.dto.image.ImageResponse;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageRepository {

    ImageResponse retrieveImage(final Long id);

    String downloadImage(final String url);

    String uploadImage(final MultipartFile image) throws IOException;
}
