package com.example.restaurantmanagement.model.address;

import com.example.restaurantmanagement.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private String id;
    private String name;
    private String city;
    private String street;
    private String apartment;
    private AddressType addressType;
    private UserWithoutAddress user;

    @Data
    public static class UserWithoutAddress{
        private String id;
        private String name;
        private String surname;
        private String email;
        private String phoneNumber;
    }
}
