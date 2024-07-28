package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.UserEntity;
import com.example.restaurantmanagement.dao.repository.UserRepository;
import com.example.restaurantmanagement.enums.VerificationStatus;
import com.example.restaurantmanagement.exceptions.IsNotValidForRegister;
import com.example.restaurantmanagement.mapper.UserMapper;
import com.example.restaurantmanagement.model.user.UserCreateDto;
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
    private final EmailVerificationService emailVerificationService;

    public List<UserDto> getAllUsers() {
        log.info("ACTION.getAllUsers.start");
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDto> userDtoList = userMapper.listToDto(userEntityList);
        log.info("ACTION.getAllUsers.end");
        return userDtoList;
    }

    public void createUser(UserCreateDto userCreateDto) {
        Boolean isValidForReg = emailVerificationService.checkValidCode(
                userCreateDto.getEmail(),
                userCreateDto.getVerificationCode()
        );

        if (!isValidForReg) {
            throw new IsNotValidForRegister(
                    userCreateDto.getVerificationCode(),
                    userCreateDto.getEmail(),
                    "This verification code is not valid",
                    String.format("ACTION.ERROR.createUser requestBody : %s", userCreateDto)
            );
        }

        UserEntity userEntity = userMapper.mapToEntity(userCreateDto);
        userRepository.save(userEntity);
        emailVerificationService.changeStatus(userCreateDto.getEmail(), VerificationStatus.VERIFICATED);
    }

}
