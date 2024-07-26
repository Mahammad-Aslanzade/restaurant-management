package com.example.restaurantmanagement.model.user;


import com.example.restaurantmanagement.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private List<AddressWithoutUser> addressList;
    private LocalDate birthDate;


    @Data
    public static class AddressWithoutUser {
        private String id;
        private String name;
        private String city;
        private String street;
        private String apartment;
        private AddressType addressType;
    }
}
