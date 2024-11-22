package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.configuration.MinioBuckets;
import com.example.restaurantmanagement.dao.entity.BannerEntity;
import com.example.restaurantmanagement.dao.repository.jpa.BannerRepository;
import com.example.restaurantmanagement.enums.ExceptionDetails;
import com.example.restaurantmanagement.exceptions.AlreadyExistException;
import com.example.restaurantmanagement.exceptions.NotFoundException;
import com.example.restaurantmanagement.mapper.BannerMapper;
import com.example.restaurantmanagement.model.banner.BannerDto;
import com.example.restaurantmanagement.model.banner.BannerReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    private BannerEntity getBannerEntity(String bannerId) {
        return bannerRepository.findById(bannerId)
                .orElseThrow(() ->
                        new NotFoundException(
                                ExceptionDetails.BANNER_NOT_FOUND.message(),
                                ExceptionDetails.BANNER_NOT_FOUND.createLogMessage("getById", bannerId)
                        )
                );
    }

    public BannerDto getById(String id) {
        log.info("ACTION.getById.start id : {}", id);
        BannerEntity bannerEntity = getBannerEntity(id);
        BannerDto bannerDto = bannerMapper.mapToDto(bannerEntity);
        log.info("ACTION.getById.end id : {}", id);
        return bannerDto;
    }

    public void createBanner(MultipartFile image , BannerReqDto bannerReqDto) {
        if (bannerRepository.getBannerEntityByNameIgnoreCase(bannerReqDto.getName()).isPresent()) {
            throw new AlreadyExistException(
                    ExceptionDetails.ALREADY_EXIST.message(),
                    ExceptionDetails.ALREADY_EXIST.createLogMessage("createBanner")
            );
        }
        BannerEntity bannerEntity = bannerMapper.mapToEntity(bannerReqDto);
        String imageUrl = imageService.upLoadImageAndGetUrl(image , MinioBuckets.BANNER);
        bannerEntity.setImage(imageUrl);
        bannerRepository.save(bannerEntity);
    }

    public void updateBanner(String bannerId, MultipartFile image , BannerReqDto bannerReqDto) {
        log.info("ACTION.updateBanner.start bannerId : {} | reqBody : {}", bannerId, bannerReqDto);
        BannerEntity bannerOld = getBannerEntity(bannerId);
        BannerEntity bannerNew = bannerMapper.mapToEntity(bannerReqDto);
        if (image == null) {
            bannerNew.setImage(bannerOld.getImage());
        } else {
            String imageUrl = imageService.upLoadImageAndGetUrl(image, MinioBuckets.BANNER);
            bannerNew.setImage(imageUrl);
            imageService.deleteImage(bannerOld.getImage());
        }
        bannerNew.setId(bannerOld.getId());
        bannerRepository.save(bannerNew);
        log.info("ACTION.updateBanner.end bannerId : {} | reqBody : {}", bannerId, bannerReqDto);
    }

    public void deleteBanner(String bannerId) {
        log.info("ACTION.deleteBanner.start bannerId : {}", bannerId);
        BannerEntity banner = getBannerEntity(bannerId);
        imageService.deleteImage(banner.getImage());
        bannerRepository.delete(banner);
        log.info("ACTION.deleteBanner.end bannerId : {}", bannerId);
    }

}
