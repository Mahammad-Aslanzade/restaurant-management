package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.aboutUs.AboutUsDto;
import com.example.restaurantmanagement.service.AboutUsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/aboutUs")
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
