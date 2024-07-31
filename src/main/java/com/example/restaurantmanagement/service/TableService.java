package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.repository.TableRepository;
import com.example.restaurantmanagement.mapper.TableMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class TableService {

    private TableMapper tableMapper;
    private TableRepository tableRepository;


}
