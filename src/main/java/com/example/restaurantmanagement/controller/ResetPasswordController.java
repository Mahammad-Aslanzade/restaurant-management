package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.auth.ResetPassReqDto;
import com.example.restaurantmanagement.model.auth.ResponseMessage;
import com.example.restaurantmanagement.model.auth.UserEmailDto;
import com.example.restaurantmanagement.service.ResetPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class ResetPasswordController {

    private final ResetPasswordService resetPasswordService;

    @PostMapping("/reset-password/get-token")
    public ResponseMessage getResetPasswordToken(@RequestBody UserEmailDto reqDto) {
        return resetPasswordService.getResetPasswordToken(reqDto);
    }

    @PostMapping("/reset-password")
    public ResponseMessage resetPassword(@RequestBody ResetPassReqDto resetPassReqDto){
        return resetPasswordService.resetPassword(resetPassReqDto);
    }

}
