package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.banner.BannerDto;
import com.example.restaurantmanagement.model.banner.BannerReqDto;
import com.example.restaurantmanagement.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("/banner")
public class BannerController {

    private final BannerService bannerService;

    @GetMapping("/{bannerName}")
    public BannerDto getByName(@PathVariable String bannerName){
        return bannerService.getByName(bannerName);
    }

    @PostMapping
    public void createBanner(@ModelAttribute BannerReqDto bannerReqDto){
        bannerService.createBanner(bannerReqDto);
    }
}
