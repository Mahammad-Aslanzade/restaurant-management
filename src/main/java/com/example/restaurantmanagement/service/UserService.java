package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.AddressEntity;
import com.example.restaurantmanagement.dao.entity.UserEntity;
import com.example.restaurantmanagement.dao.repository.UserRepository;
import com.example.restaurantmanagement.enums.ExceptionDetails;
import com.example.restaurantmanagement.enums.Role;
import com.example.restaurantmanagement.enums.VerificationStatus;
import com.example.restaurantmanagement.exceptions.AlreadyExistException;
import com.example.restaurantmanagement.exceptions.IsNotValidForRegister;
import com.example.restaurantmanagement.exceptions.NotFoundException;
import com.example.restaurantmanagement.mapper.UserMapper;
import com.example.restaurantmanagement.model.user.UserCreateDto;
import com.example.restaurantmanagement.model.user.UserDto;
import com.example.restaurantmanagement.model.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final EmailVerificationService emailVerificationService;
    private final PasswordEncoder passwordEncoder;


    public List<UserDto> getAllUsers() {
        log.info("ACTION.getAllUsers.start");
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDto> userDtoList = userMapper.listToDto(userEntityList);
        log.info("ACTION.getAllUsers.end");
        return userDtoList;
    }

    public UserEntity getUserEntity(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(
                        ExceptionDetails.USER_NOT_FOUND.message(),
                        ExceptionDetails.USER_NOT_FOUND.createLogMessage("getUserEntity", userId)
                )
        );
    }

    public UserDto getUserById(String userId) {
        log.info("ACTION.getUserById.start userId : {}", userId);
        UserEntity userEntity = getUserEntity(userId);
        UserDto userDto = userMapper.mapToDto(userEntity);
        log.info("ACTION.getUserById.end userId : {}", userId);
        return userDto;
    }

//    public void createUser(UserCreateDto userCreateDto) {
//        log.info("ACTION.createUser.start requestBody : {}", userCreateDto);
//
//        // Checking user table if any same email exist
//        if (userRepository.findByEmail(userCreateDto.getEmail()).isPresent()) {
//            throw new AlreadyExistException(
//                    ExceptionDetails.THIS_EMAIL_IS_ALREADY_EXIST.message(),
//                    ExceptionDetails.THIS_EMAIL_IS_ALREADY_EXIST.createLogMessage("createUser", "email", userCreateDto.getEmail())
//            );
//        }
//
//        // Check code is valid
//        Boolean isValidForReg = emailVerificationService.checkValidCode(
//                userCreateDto.getEmail(),
//                userCreateDto.getVerificationCode()
//        );
//        if (!isValidForReg) {
//            throw new IsNotValidForRegister(
//                    userCreateDto.getVerificationCode(),
//                    userCreateDto.getEmail(),
//                    "This verification code is not valid",
//                    String.format("ACTION.ERROR.createUser requestBody : %s", userCreateDto)
//            );
//        }
//
//        UserEntity userEntity = userMapper.mapToEntity(userCreateDto);
//        userEntity.setRole(Role.USER);
//        userEntity.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
//        userRepository.save(userEntity);
//        emailVerificationService.changeStatus(userCreateDto.getEmail(), VerificationStatus.VERIFICATED);
//        log.info("ACTION.createUser.end requestBody : {}", userCreateDto);
//    }
    public UserDto createUser(UserCreateDto userCreateDto , Role role) {
    log.info("ACTION.createUser.start requestBody : {}", userCreateDto);

//    // Checking user table if any same email exist
//    if (userRepository.findByEmail(userCreateDto.getEmail()).isPresent()) {
//        throw new AlreadyExistException(
//                ExceptionDetails.THIS_EMAIL_IS_ALREADY_EXIST.message(),
//                ExceptionDetails.THIS_EMAIL_IS_ALREADY_EXIST.createLogMessage("createUser", "email", userCreateDto.getEmail())
//        );
//    }
//
//    // Check code is valid
//    Boolean isValidForReg = emailVerificationService.checkValidCode(
//            userCreateDto.getEmail(),
//            userCreateDto.getVerificationCode()
//    );
//    if (!isValidForReg) {
//        throw new IsNotValidForRegister(
//                userCreateDto.getVerificationCode(),
//                userCreateDto.getEmail(),
//                "This verification code is not valid",
//                String.format("ACTION.ERROR.createUser requestBody : %s", userCreateDto)
//        );
//    }

    UserEntity userEntity = userMapper.mapToEntity(userCreateDto);
    userEntity.setId(UUID.randomUUID().toString());
    userEntity.setUsername(userEntity.getEmail());
    userEntity.setRole(role);
    userEntity.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
    userRepository.save(userEntity);
//    emailVerificationService.changeStatus(userCreateDto.getEmail(), VerificationStatus.VERIFICATED);
    log.info("ACTION.createUser.end requestBody : {}", userCreateDto);
    return userMapper.mapToDto(userEntity);
}


    public void updateUser(String userId, UserUpdateDto userUpdateDto) {
        log.info("ACTION.updateUser.start id : {} | reqBody : {}", userId, userUpdateDto);
        UserEntity oldUser = getUserEntity(userId);
        UserEntity updatedUser = userMapper.mapToEntity(userUpdateDto);
        updatedUser.setId(oldUser.getId());
        // email cann't be updated | it will be add with verification like registering soon
        updatedUser.setEmail(oldUser.getEmail());
        updatedUser.setAddressList(oldUser.getAddressList());
        userRepository.save(updatedUser);
        log.info("ACTION.updateUser.end id : {} | reqBody : {}", userId, userUpdateDto);
    }

    public void deleteUser(String userId) {
        log.info("ACTION.deleteUser.start userId : {}", userId);
        UserEntity user = getUserEntity(userId);
        userRepository.delete(user);
        log.info("ACTION.deleteUser.end userId : {}", userId);
    }

    public AddressEntity haveThisAddress(String userId, String addressId) {
        UserEntity user = getUserEntity(userId);
        List<AddressEntity> addressList = user.getAddressList();

        for (AddressEntity address : addressList) {
            if (address.getId().equals(addressId)) {
                return address;
            }
        }

        throw new NotFoundException(
                ExceptionDetails.INVALID_ADDRESS.message(),
                String.format("ACTION.ERROR.haveThisAddress userId : %s | addressId : %s", userId, addressId)
        );
    }

    public UserDto registerUser(UserCreateDto userCreateDto , Role role) {
        UserEntity user = userMapper.mapToEntity(userCreateDto);
        String password = passwordEncoder.encode(userCreateDto.getPassword());
        user.setId(UUID.randomUUID().toString());
        user.setRole(role);
        user.setPassword(password);
        userRepository.save(user);
        return userMapper.mapToDto(user);
    }

}
