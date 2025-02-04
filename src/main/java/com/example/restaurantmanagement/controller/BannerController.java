package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.banner.BannerDto;
import com.example.restaurantmanagement.model.banner.BannerReqDto;
import com.example.restaurantmanagement.service.BannerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/banners")
public class BannerController {

    private final BannerService bannerService;

    @GetMapping
    public List<BannerDto> getAllBanners(){
        return bannerService.getAllBanners();
    }

    @GetMapping("/{banner-id}")
    public BannerDto getById(@PathVariable("banner-id") String bannerId){
        return bannerService.getById(bannerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBanner(@RequestPart("image") MultipartFile image , @RequestPart("bannerDetail") @Valid BannerReqDto bannerReqDto){
        bannerService.createBanner(image , bannerReqDto);
    }

    @PutMapping("/{banner-id}")
    public void updateBanner(@PathVariable("banner-id") String bannerId , @RequestPart(value = "image",required = false) MultipartFile image , @RequestPart("bannerDetail") @Valid BannerReqDto bannerReqDto){
        bannerService.updateBanner(bannerId , image , bannerReqDto);
    }

    @DeleteMapping("/{banner-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBanner(@PathVariable("banner-id") String bannerId){
        bannerService.deleteBanner(bannerId);
    }
}
