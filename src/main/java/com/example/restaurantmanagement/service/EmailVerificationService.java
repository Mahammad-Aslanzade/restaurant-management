package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.EmailVerificationEntity;
import com.example.restaurantmanagement.dao.repository.EmailVerificationRepository;
import com.example.restaurantmanagement.dao.repository.UserRepository;
import com.example.restaurantmanagement.enums.ExceptionDetails;
import com.example.restaurantmanagement.enums.VerificationStatus;
import com.example.restaurantmanagement.exceptions.AlreadyExistException;
import com.example.restaurantmanagement.mapper.EmailVerificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmailVerificationService {

    private final EmailService emailService;
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailVerificationMapper emailVerificationMapper;
    private final UserRepository userRepository;


    private static final Random random = new Random();

    public void verificateEmail(String email) {
        EmailVerificationEntity lastRequest = emailVerificationRepository.findLatestEntity();
        LocalDateTime twoMinuteAge = LocalDateTime.now().minusMinutes(2);
        String generatedCode = random.nextInt(1000, 9999) + "-" + random.nextInt(1000, 9999);

        if (userRepository.findUserEntitiesByEmail(email).isPresent()) {
            throw new AlreadyExistException(
                    ExceptionDetails.THIS_EMAIL_IS_ALREADY_EXIST.message(),
                    ExceptionDetails.THIS_EMAIL_IS_ALREADY_EXIST.createLogMessage("verificateEmail" , "email" , email)
            );
        }


        if (lastRequest == null || lastRequest.getIssueDate().isBefore(twoMinuteAge)) {
            emailService.postEmail(email,
                    "Restaurant management app",
                    String.format("Your verification code is  **%s**", generatedCode)
            );

            EmailVerificationEntity verificatedEntity = EmailVerificationEntity.builder()
                    .email(email)
                    .verificationCode(generatedCode)
                    .issueDate(LocalDateTime.now())
                    .verificationStatus(VerificationStatus.PENDING)
                    .build();
            emailVerificationRepository.save(verificatedEntity);
        }else{
            throw  new AlreadyExistException(
                    "Verification session has opened yet! Your verificitaion code already exist",
                    String.format("ACTION.ERROR.verificateEmail email : %s" , email)
            );
        }
    }
}
