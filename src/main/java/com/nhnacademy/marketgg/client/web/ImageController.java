package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.service.ImageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/editor")
    public String uploadImage(@RequestPart(value = "image") MultipartFile image) throws IOException {
        String s = imageService.uploadImage(image);

        return s;
    }

}
