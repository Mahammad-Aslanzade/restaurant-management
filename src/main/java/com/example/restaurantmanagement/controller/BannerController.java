package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.banner.BannerDto;
import com.example.restaurantmanagement.model.banner.BannerReqDto;
import com.example.restaurantmanagement.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/banner")
public class BannerController {

    private final BannerService bannerService;

    @GetMapping
    public List<BannerDto> getAllBanners(){
        return bannerService.getAllBanners();
    }

    @GetMapping("/{bannerName}")
    public BannerDto getByName(@PathVariable String bannerName){
        return bannerService.getByName(bannerName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBanner(@ModelAttribute BannerReqDto bannerReqDto){
        bannerService.createBanner(bannerReqDto);
    }

    @PutMapping("/{bannerName}")
    public void updateBanner(@PathVariable String bannerName , @ModelAttribute BannerReqDto bannerReqDto){
        bannerService.updateBanner(bannerName , bannerReqDto);
    }

    @DeleteMapping("/{bannerName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBanner(@PathVariable String bannerName){
        bannerService.deleteBanner(bannerName);
    }
}
