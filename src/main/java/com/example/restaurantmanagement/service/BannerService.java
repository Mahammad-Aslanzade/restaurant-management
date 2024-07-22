package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.BannerEntity;
import com.example.restaurantmanagement.dao.repository.BannerRepository;
import com.example.restaurantmanagement.enums.ExceptionDetails;
import com.example.restaurantmanagement.exceptions.AlreadyExistException;
import com.example.restaurantmanagement.exceptions.NotFoundException;
import com.example.restaurantmanagement.mapper.BannerMapper;
import com.example.restaurantmanagement.model.banner.BannerDto;
import com.example.restaurantmanagement.model.banner.BannerReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Service
public class BannerService {

    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;

    private final ImageService imageService;

    public BannerDto getByName(String name) {
        log.info("ACTION.getByName.start name : {}", name);
        BannerEntity bannerEntity = bannerRepository.getBannerEntityByNameIgnoreCase(name)
                .orElseThrow(() ->
                        new NotFoundException(
                                ExceptionDetails.BANNER_NOT_FOUND.message(),
                                ExceptionDetails.BANNER_NOT_FOUND.createLogMessage("getByName", "name", name)
                        )
                );
        BannerDto bannerDto = bannerMapper.mapToDto(bannerEntity);
        log.info("ACTION.getByName.end name : {}", name);
        return bannerDto;
    }

    public void createBanner(BannerReqDto bannerReqDto){
        if(bannerRepository.getBannerEntityByNameIgnoreCase(bannerReqDto.getName()).isPresent()){
            throw new AlreadyExistException(
                    ExceptionDetails.ALREADY_EXIST.message(),
                    ExceptionDetails.ALREADY_EXIST.createLogMessage("createBanner")
            );
        }
        BannerEntity bannerEntity = bannerMapper.mapToEntity(bannerReqDto);
        String imageUrl = null;
        try {
            System.out.println(bannerReqDto.getImage().getOriginalFilename());
            imageUrl = imageService.upLoadImageAndGetPath(bannerReqDto.getImage());
        } catch (IOException e) {
            throw new RuntimeException("IMAGE_CANNOT_BE_UPLOADED");
        }
        bannerEntity.setImage(imageUrl);
        bannerRepository.save(bannerEntity);
    }
}
