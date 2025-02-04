package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.aboutUs.AboutUsDto;
import com.example.restaurantmanagement.service.AboutUsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/about-us")
public class AboutUsController {

    private final AboutUsService aboutUsService;

    @GetMapping
    public AboutUsDto getAboutUs() {
        return aboutUsService.getAboutUs();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAboutUs(@RequestBody @Valid AboutUsDto aboutUsDto) {
        aboutUsService.createAboutUs(aboutUsDto);
    }

    @PutMapping
    public void updateAboutUs(@RequestBody @Valid AboutUsDto aboutUsDto) {
        aboutUsService.updateAboutUs(aboutUsDto);
    }
}
