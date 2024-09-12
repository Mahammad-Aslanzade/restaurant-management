package com.example.restaurantmanagement.controller;


import com.example.restaurantmanagement.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/uploads/{fileName}")
    public Resource getImage(@PathVariable String fileName) {
        return imageService.getImage(fileName);
    }


}
