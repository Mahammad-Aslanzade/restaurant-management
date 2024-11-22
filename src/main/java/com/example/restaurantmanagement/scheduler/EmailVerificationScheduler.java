package com.example.restaurantmanagement.scheduler;

import com.example.restaurantmanagement.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationScheduler {

    private final EmailVerificationService emailVerificationService;


    @Scheduled(fixedRate = 60000) // every 1min
    public void defineExpiredCodes(){
        log.info("ACTION.SCHEDULER.defineExpiredCodes.start");
        emailVerificationService.defineExpiredCodes();
        log.info("ACTION.SCHEDULER.defineExpiredCodes.end");
    }
}
