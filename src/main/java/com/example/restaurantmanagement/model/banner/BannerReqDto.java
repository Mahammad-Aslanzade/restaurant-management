package com.example.restaurantmanagement.model.banner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerReqDto {
    private String name;
    private String text;
    private MultipartFile image;
}
