package com.example.restaurantmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deploy")
public class TestController {

    @GetMapping
    private String deployTest(){
        return "v1/test/is-running/in unibank";
    }
}
