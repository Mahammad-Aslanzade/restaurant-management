package com.example.restaurantmanagement.model.address;

import com.example.restaurantmanagement.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDetailDto {
    private String id;
    private String name;
    private String city;
    private String street;
    private String apartment;
    private AddressType addressType;
}
