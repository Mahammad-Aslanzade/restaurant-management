package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.AddressEntity;
import com.example.restaurantmanagement.dao.entity.UserEntity;
import com.example.restaurantmanagement.dao.repository.jpa.UserRepository;
import com.example.restaurantmanagement.enums.ExceptionDetails;
import com.example.restaurantmanagement.enums.Role;
import com.example.restaurantmanagement.enums.VerificationStatus;
import com.example.restaurantmanagement.exceptions.AlreadyExistException;
import com.example.restaurantmanagement.exceptions.InvalidException;
import com.example.restaurantmanagement.exceptions.IsNotValidForRegister;
import com.example.restaurantmanagement.exceptions.NotFoundException;
import com.example.restaurantmanagement.mapper.UserMapper;
import com.example.restaurantmanagement.model.auth.ResetPassReqDto;
import com.example.restaurantmanagement.model.auth.ResponseMessage;
import com.example.restaurantmanagement.model.auth.UserEmailDto;
import com.example.restaurantmanagement.model.user.UserCreateDto;
import com.example.restaurantmanagement.model.user.UserDto;
import com.example.restaurantmanagement.model.user.UserUpdateDto;
import com.example.restaurantmanagement.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.context.Context;

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
    private final EmailService emailService;
    private final JwtTokenUtil jwtTokenUtil;
    private final MyUserDetailService userDetailService;


    public List<UserDto> getAllUsers() {
        log.info("ACTION.getAllUsers.start");
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDto> userDtoList = userMapper.listToDto(userEntityList);
        log.info("ACTION.getAllUsers.end");
        return userDtoList;
    }

    public List<UserDto> getCustomers() {
        log.info("ACTION.getCustomers.start");
        List<UserEntity> userEntityList = userRepository.findAllByRole(Role.USER);
        List<UserDto> userDtoList = userMapper.listToDto(userEntityList);
        log.info("ACTION.getCustomers.end");
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

    public UserEntity getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException(
                        ExceptionDetails.USER_NOT_FOUND.message(),
                        ExceptionDetails.USER_NOT_FOUND.createLogMessage("getUserEntity", "email", email)
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

    public UserDto createUser(UserCreateDto userCreateDto, Role role) {
        log.info("ACTION.createUser.start requestBody : {}", userCreateDto);

        // Checking user table if any same email exist
        if (userRepository.findByEmail(userCreateDto.getEmail()).isPresent()) {
            throw new AlreadyExistException(
                    ExceptionDetails.THIS_EMAIL_IS_ALREADY_EXIST.message(),
                    ExceptionDetails.THIS_EMAIL_IS_ALREADY_EXIST.createLogMessage("createUser", "email", userCreateDto.getEmail())
            );
        }

    // Check code is valid
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
        userEntity.setRole(role);
        userEntity.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        userRepository.save(userEntity);
        emailVerificationService.changeStatus(userCreateDto.getEmail(), VerificationStatus.VERIFICATED);
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
        updatedUser.setPassword(oldUser.getPassword());
        updatedUser.setRole(oldUser.getRole());
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

    public AddressEntity haveThisAddress(UserEntity user, String addressId) {
        List<AddressEntity> addressList = user.getAddressList();
        for (AddressEntity address : addressList) {
            if (address.getId().equals(addressId)) {
                return address;
            }
        }

        throw new NotFoundException(
                ExceptionDetails.INVALID_ADDRESS.message(),
                String.format("ACTION.ERROR.haveThisAddress userId : %s | addressId : %s", user.getId(), addressId)
        );
    }

    public UserDto registerUser(UserCreateDto userCreateDto, Role role) {
        UserEntity user = userMapper.mapToEntity(userCreateDto);
        String password = passwordEncoder.encode(userCreateDto.getPassword());
        user.setId(UUID.randomUUID().toString());
        user.setRole(role);
        user.setPassword(password);
        userRepository.save(user);
        return userMapper.mapToDto(user);
    }

    public ResponseMessage getResetPasswordToken(UserEmailDto reqDto) {
        String email = reqDto.getEmail();

        log.info("ACTION.getResetPasswordToken.start email : {}", email);

        // Check user exist?
        UserEntity user = getUserEntityByEmail(email);

        //Genereate token
        UserDetails userDetails = userDetailService.loadUserByUsername(email);
        String token = jwtTokenUtil.generateToken(userDetails);

        // Email action
        String templateName = "reset-password";
        String redirectUrl =  String.format("http://localhost:5173/user/resetPassword/%s" , token);
        Context context = new Context();
        context.setVariable("resetUrl", redirectUrl);
        context.setVariable("userName" , user.getName());
        emailService.sendEmailWithHtmlTemplate(
                user.getEmail(), "Reset password" ,
                templateName , context
        );

        log.info("ACTION.getResetPasswordToken.start email : {}", email);
        return new ResponseMessage("Operation completed successfully !");
    }

    public ResponseMessage resetPassword(ResetPassReqDto resetPassReqDto){
        UserEntity client = userDetailService.getCurrentAuthenticatedUser();
        log.info("ACTION.resetPassword.start email : {}", client.getEmail());

        if( !resetPassReqDto.getPassword().equals(resetPassReqDto.getConfirmPassword())){
            throw new InvalidException(
                    "confirmPassword" , "passwords don't match",
                    String.format("ACTION.ERROR.resetPassword  email : %s | message : password don't match" , client.getEmail())
            );
        }

        client.setPassword(passwordEncoder.encode(resetPassReqDto.getPassword()));
        userRepository.save(client);

        log.info("ACTION.resetPassword.end email : {}", client.getEmail());
        return new ResponseMessage("Operation completed successfully !");
    }

}
