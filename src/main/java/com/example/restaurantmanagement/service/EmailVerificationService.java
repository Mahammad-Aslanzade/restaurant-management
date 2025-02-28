package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.EmailVerificationEntity;
import com.example.restaurantmanagement.dao.repository.jpa.EmailVerificationRepository;
import com.example.restaurantmanagement.dao.repository.jpa.UserRepository;
import com.example.restaurantmanagement.enums.ExceptionDetails;
import com.example.restaurantmanagement.enums.VerificationStatus;
import com.example.restaurantmanagement.exceptions.AlreadyExistException;
import com.example.restaurantmanagement.exceptions.InvalidException;
import com.example.restaurantmanagement.exceptions.NotFoundException;
import com.example.restaurantmanagement.model.auth.ResponseMessage;
import com.example.restaurantmanagement.model.user.VerifyEmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmailVerificationService {

    private final EmailService emailService;
    private final EmailVerificationRepository emailVerificationRepository;
    private final UserRepository userRepository;
    private static final Random random = new Random();
    private static final Integer EXPIRE_TIME = 3;

    public ResponseMessage verifyEmail(VerifyEmailDto emailDto) {
        String email = emailDto.getEmail();
        log.info("ACTION.verifyEmail.start email : {}", email);

        //Does user exist with this email (checking process)
        if (userRepository.findByEmail(email).isPresent()) {
            throw new AlreadyExistException(
                    ExceptionDetails.THIS_EMAIL_IS_ALREADY_EXIST.message(),
                    ExceptionDetails.THIS_EMAIL_IS_ALREADY_EXIST.createLogMessage("verificateEmail", "email", email)
            );
        }

        Optional<EmailVerificationEntity> lastRequest = emailVerificationRepository.findLatestEntity(email);

        //Checking request if expired
        if(lastRequest.isPresent() && lastRequest.get().getIssueDate().isBefore(getDefinedMinuteAgo()) && lastRequest.get().getVerificationStatus() != VerificationStatus.FAILED){
            lastRequest.get().setVerificationStatus(VerificationStatus.FAILED);
            emailVerificationRepository.save(lastRequest.get());
            log.info(String.format("ACTION.ERROR: expired verification code is detected email:%s", email));
        }

        //
        if (lastRequest.isPresent() && lastRequest.get().getIssueDate().isAfter(getDefinedMinuteAgo())) {
            throw new AlreadyExistException(
                    "Verification session has opened yet! Your verificitaion code already exist",
                    String.format("ACTION.ERROR.verificateEmail email : %s", email)
            );
        }

        //Settings of html template and pass it to emailService
        String templateName = "email-verification";
        String generatedCode = random.nextInt(1000, 9999) + "-" + random.nextInt(1000, 9999);
        Context context = new Context();
        context.setVariable("verificationCode", generatedCode);
        emailService.sendEmailWithHtmlTemplate(
                email,
                "Restaurant App Verification",
                templateName, context
        );

        //We save details to database at the end
        //Because it can be some problem during sending email
        EmailVerificationEntity verificatedEntity = new EmailVerificationEntity();
        verificatedEntity.setEmail(email);
        verificatedEntity.setVerificationCode(generatedCode);
        verificatedEntity.setIssueDate(LocalDateTime.now());
        verificatedEntity.setVerificationStatus(VerificationStatus.PENDING);

        emailVerificationRepository.save(verificatedEntity);
        log.info("ACTION.verifyEmail.end email : {}", email);
        return new ResponseMessage("Successfully operation !");
    }

    public Boolean checkValidCode(String email, String code) {
        Optional<EmailVerificationEntity> lastRequest = emailVerificationRepository.findLatestEntity(email);
        return lastRequest.isPresent() && lastRequest.get().getIssueDate().isAfter(getDefinedMinuteAgo()) && lastRequest.get().getVerificationCode().equals(code);
    }

    public void changeStatus(String email, VerificationStatus verificationStatus) {
        EmailVerificationEntity emailVerification = emailVerificationRepository.findLatestEntity(email)
                .orElseThrow(() ->
                        new NotFoundException(
                                ExceptionDetails.EMAIL_NOT_FOUND.message(),
                                ExceptionDetails.EMAIL_NOT_FOUND.createLogMessage("changeStatus", "email", email)
                        )
                );
        emailVerification.setVerificationStatus(verificationStatus);
        emailVerificationRepository.save(emailVerification);
    }

    private LocalDateTime getDefinedMinuteAgo(){
        return LocalDateTime.now().minusMinutes(EXPIRE_TIME);
    }
}
