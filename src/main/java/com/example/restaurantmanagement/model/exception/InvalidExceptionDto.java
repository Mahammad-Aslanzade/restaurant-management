package com.example.restaurantmanagement.model.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvalidExceptionDto {
    private String parameter;
    private String message;
}
