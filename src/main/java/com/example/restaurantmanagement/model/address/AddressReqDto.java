package com.example.restaurantmanagement.model.address;

import com.example.restaurantmanagement.enums.AddressType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressReqDto {
    private String userId;
    private String name;
    @NotNull
    private String city;
    @NotNull
    private String street;
    @NotNull
    private String apartment;
    private AddressType addressType;
}
