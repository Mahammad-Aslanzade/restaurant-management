package com.example.restaurantmanagement.model.banner;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerReqDto {
    @NotNull(message = "name cann't be null")
    private String name;
    @NotNull(message = "text cann't be null")
    private String text;
}
