package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RestController
@RequestMapping("/table")
public class TableController {

    private final TableService tableService;
}
