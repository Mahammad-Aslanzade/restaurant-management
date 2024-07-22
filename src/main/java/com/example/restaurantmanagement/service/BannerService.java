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

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class BannerService {

    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;
    private final ImageService imageService;


    public List<BannerDto> getAllBanners() {
        log.info("ACTION.getAllBanners.strat");
        List<BannerEntity> bannerEntityList = bannerRepository.findAll();
        List<BannerDto> bannerDtoList = bannerMapper.listToDto(bannerEntityList);
        log.info("ACTION.getAllBanners.end");
        return bannerDtoList;
    }

    private BannerEntity getBannerEntity(String bannerName) {
        return bannerRepository.getBannerEntityByNameIgnoreCase(bannerName)
                .orElseThrow(() ->
                        new NotFoundException(
                                ExceptionDetails.BANNER_NOT_FOUND.message(),
                                ExceptionDetails.BANNER_NOT_FOUND.createLogMessage("getByName", "name", bannerName)
                        )
                );
    }

    public BannerDto getByName(String name) {
        log.info("ACTION.getByName.start name : {}", name);
        BannerEntity bannerEntity = getBannerEntity(name);
        BannerDto bannerDto = bannerMapper.mapToDto(bannerEntity);
        log.info("ACTION.getByName.end name : {}", name);
        return bannerDto;
    }

    public void createBanner(BannerReqDto bannerReqDto) {
        if (bannerRepository.getBannerEntityByNameIgnoreCase(bannerReqDto.getName()).isPresent()) {
            throw new AlreadyExistException(
                    ExceptionDetails.ALREADY_EXIST.message(),
                    ExceptionDetails.ALREADY_EXIST.createLogMessage("createBanner")
            );
        }
        BannerEntity bannerEntity = bannerMapper.mapToEntity(bannerReqDto);
        String imageUrl;
        try {
            imageUrl = imageService.upLoadImageAndGetPath(bannerReqDto.getImage());
        } catch (IOException e) {
            throw new RuntimeException("IMAGE_CANNOT_BE_UPLOADED");
        }
        bannerEntity.setImage(imageUrl);
        bannerRepository.save(bannerEntity);
    }

    public void updateBanner(String bannerName, BannerReqDto bannerReqDto) {
        log.info("ACTION.updateBanner.start bannerName : {} | reqBody : {}", bannerName, bannerReqDto);
        BannerEntity bannerOld = getBannerEntity(bannerName);
        BannerEntity bannerNew = bannerMapper.mapToEntity(bannerReqDto);
        if (bannerReqDto.getImage() == null) {
            bannerNew.setImage(bannerOld.getImage());
        } else {
            String imageUrl;
            try {
                imageUrl = imageService.upLoadImageAndGetPath(bannerReqDto.getImage());
                bannerNew.setImage(imageUrl);
                imageService.deleteImage(bannerOld.getImage());
            } catch (IOException e) {
                throw new RuntimeException("IMAGE_CANNOT_BE_UPLOADED");
            }
        }
        bannerNew.setId(bannerOld.getId());
        bannerRepository.save(bannerNew);
        log.info("ACTION.updateBanner.end bannerName : {} | reqBody : {}", bannerName, bannerReqDto);
    }

    public void deleteBanner(String bannerName) {
        log.info("ACTION.deleteBanner.start bannerName : {}", bannerName);
        BannerEntity banner = getBannerEntity(bannerName);
        imageService.deleteImage(banner.getImage());
        bannerRepository.delete(banner);
        log.info("ACTION.deleteBanner.end bannerName : {}", bannerName);
    }

}
