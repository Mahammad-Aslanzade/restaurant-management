package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.AboutUsEntity;
import com.example.restaurantmanagement.dao.repository.AboutUsRepository;
import com.example.restaurantmanagement.exceptions.AlreadyExistException;
import com.example.restaurantmanagement.exceptions.NotFoundException;
import com.example.restaurantmanagement.mapper.AboutUsMapper;
import com.example.restaurantmanagement.model.aboutUs.AboutUsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AboutUsService {

    private final AboutUsMapper aboutUsMapper;
    private final AboutUsRepository aboutUsRepository;


    public AboutUsDto getAboutUs() {
        log.info("ACTION.getAboutUs.start");
        AboutUsEntity aboutUsEntity = aboutUsRepository.findById(1)
                .orElseThrow(() ->
                        new NotFoundException(
                                "ABOUT_US_NOT_FOUND",
                                "ACTION.ERROR.defineAboutUs"
                        )
                );
        AboutUsDto aboutUsDto = aboutUsMapper.mapToDto(aboutUsEntity);
        log.info("ACTION.getAboutUs.end");
        return aboutUsDto;
    }


    public void createAboutUs(AboutUsDto aboutUsDto) {
        log.info("ACTION.createAboutUs.start requestBody : {}", aboutUsDto);
        if(aboutUsRepository.findById(1).isPresent()){
            throw new AlreadyExistException(
                    "ABOUT_US_ALREADY_EXIST",
                    "ACTION.ERROR.createAbouUs (already_exist)"
            );
        }
        AboutUsEntity aboutUsEntity = aboutUsMapper.mapToEntity(aboutUsDto);
        aboutUsEntity.setId(1);
        aboutUsRepository.save(aboutUsEntity);
        log.info("ACTION.createAboutUs.end requestBody : {}", aboutUsDto);
    }

    public void updateAboutUs(AboutUsDto aboutUsDto) {
        log.info("ACTION.updateAboutUs.start requestBody : {}", aboutUsDto);
        AboutUsEntity aboutUsEntity = aboutUsMapper.mapToEntity(aboutUsDto);
        aboutUsEntity.setId(1);
        aboutUsRepository.save(aboutUsEntity);
        log.info("ACTION.updateAboutUs.end requestBody : {}", aboutUsDto);
    }
}
