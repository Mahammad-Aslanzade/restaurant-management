package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.ResetPasswordEntity;
import com.example.restaurantmanagement.dao.entity.UserEntity;
import com.example.restaurantmanagement.dao.repository.jpa.ResetPasswordRepository;
import com.example.restaurantmanagement.dao.repository.jpa.UserRepository;
import com.example.restaurantmanagement.enums.ExceptionDetails;
import com.example.restaurantmanagement.exceptions.InvalidException;
import com.example.restaurantmanagement.exceptions.NotAllowedException;
import com.example.restaurantmanagement.exceptions.NotFoundException;
import com.example.restaurantmanagement.model.auth.ResetPassReqDto;
import com.example.restaurantmanagement.model.auth.ResponseMessage;
import com.example.restaurantmanagement.model.auth.UserEmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResetPasswordService {
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ResetPasswordRepository resetPasswordRepository;

    public ResponseMessage getResetPasswordToken(UserEmailDto reqDto) {
        String email = reqDto.getEmail();

        log.info("ACTION.getResetPasswordToken.start email : {}", email);

        // Check user exist?
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException(
                        ExceptionDetails.USER_NOT_FOUND.message(),
                        ExceptionDetails.USER_NOT_FOUND.createLogMessage("getUserEntity", "email", email)
                )
        );

        String resetPasswordKey = String.format("%s-%s", user.getName().toLowerCase(), UUID.randomUUID());

        // Email action
        String templateName = "reset-password";
        String redirectUrl = String.format("https://reset.frango.software/account/reset-password/%s", resetPasswordKey);
        Context context = new Context();
        context.setVariable("resetUrl", redirectUrl);
        context.setVariable("userName", user.getName());
        emailService.sendEmailWithHtmlTemplate(
                user.getEmail(), "Reset password",
                templateName, context
        );

        ResetPasswordEntity resetPassword = new ResetPasswordEntity();
        resetPassword.setUser(user);
        resetPassword.setValidationCode(resetPasswordKey);
        resetPasswordRepository.save(resetPassword);

        log.info("ACTION.getResetPasswordToken.start email : {}", email);
        return new ResponseMessage("Operation completed successfully !");
    }

    public ResponseMessage resetPassword(ResetPassReqDto resetPassReqDto) {
        log.info("ACTION.resetPassword.start validation-code : {}", resetPassReqDto.getValidationCode());

        ResetPasswordEntity resetPasswordEntity = resetPasswordRepository.findByValidationCode(resetPassReqDto.getValidationCode())
                .orElseThrow(() ->
                        new NotFoundException(
                                ExceptionDetails.VERIFICATION_NOT_FOUND.message(),
                                ExceptionDetails.USER_NOT_FOUND.createLogMessage("resetPassword", "verification-code", resetPassReqDto.getValidationCode()
                                )
                        ));

        if (resetPasswordEntity.getConfirmedAt() != null) {
            throw new InvalidException(
                    "validationCode",
                    ExceptionDetails.INVALID_VALIDATION_CODE.message(),
                    ExceptionDetails.INVALID_ADDRESS.createLogMessage("resetPassword")
            );
        }

        UserEntity client = resetPasswordEntity.getUser();

        if (!resetPassReqDto.getPassword().equals(resetPassReqDto.getConfirmPassword())) {
            throw new InvalidException(
                    "confirmPassword", "passwords don't match",
                    String.format("ACTION.ERROR.resetPassword  email : %s | message : password don't match", client.getEmail())
            );
        }

        client.setPassword(passwordEncoder.encode(resetPassReqDto.getPassword()));
        userRepository.save(client);

        resetPasswordEntity.setConfirmedAt(LocalDateTime.now());
        resetPasswordRepository.save(resetPasswordEntity);
        log.info("ACTION.resetPassword.end validation-code : {} | email : {}", resetPassReqDto.getValidationCode(), client.getEmail());
        return new ResponseMessage("Operation completed successfully !");
    }
}
