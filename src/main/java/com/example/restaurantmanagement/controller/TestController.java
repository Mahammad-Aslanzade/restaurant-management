package com.example.restaurantmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/dev/test")
public class TestController {
    
    @GetMapping
    public Map<String, String> getDevUrl(){
        return Map.of("minioUrl", "minio.frango.software");
    }
}
