package com.nhnacademy.marketgg.client.web.product;

import com.nhnacademy.marketgg.client.service.image.ImageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/editor")
    public String uploadImage(@RequestPart(value = "image") MultipartFile image) throws IOException {

        return imageService.uploadImage(image);
    }

}
