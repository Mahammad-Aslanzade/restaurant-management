package com.example.restaurantmanagement.model.banner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerDto {
    private String id;
    private String name;
    private String text;
    private String image;
}
