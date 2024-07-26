package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.UserEntity;
import com.example.restaurantmanagement.dao.repository.UserRepository;
import com.example.restaurantmanagement.mapper.UserMapper;
import com.example.restaurantmanagement.model.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        log.info("ACTION.getAllUsers.start");
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDto> userDtoList = userMapper.listToDto(userEntityList);
        log.info("ACTION.getAllUsers.end");
        return userDtoList;
    }
}
