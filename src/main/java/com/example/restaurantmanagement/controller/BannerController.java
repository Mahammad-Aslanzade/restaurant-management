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
@RequestMapping("/banner")
public class BannerController {

    private final BannerService bannerService;

    @GetMapping
    public List<BannerDto> getAllBanners(){
        return bannerService.getAllBanners();
    }

    @GetMapping("/{bannerId}")
    public BannerDto getById(@PathVariable String bannerId){
        return bannerService.getById(bannerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBanner(@RequestPart("image") MultipartFile image , @RequestPart("bannerDetail") @Valid BannerReqDto bannerReqDto){
        bannerService.createBanner(image , bannerReqDto);
    }

    @PutMapping("/{bannerId}")
    public void updateBanner(@PathVariable String bannerId , @RequestPart(value = "image",required = false) MultipartFile image , @RequestPart("bannerDetail") @Valid BannerReqDto bannerReqDto){
        bannerService.updateBanner(bannerId , image , bannerReqDto);
    }

    @DeleteMapping("/{bannerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBanner(@PathVariable String bannerId){
        bannerService.deleteBanner(bannerId);
    }
}
