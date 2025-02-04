package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.address.AddressDetailDto;
import com.example.restaurantmanagement.model.address.AddressDto;
import com.example.restaurantmanagement.model.address.AddressReqDto;
import com.example.restaurantmanagement.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public List<AddressDto> getAllAddresses() {
        return addressService.getAllAddreses();
    }

    @GetMapping("/current-user")
    public List<AddressDetailDto> getUserAddress(){
        return addressService.getAddresesByUser();
    }

    @GetMapping("/{address-id}")
    public AddressDto getAddressById(@PathVariable("address-id") String addressId) {
        return addressService.getAddressById(addressId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDto createAddress(@RequestBody @Valid AddressReqDto addressReqDto) {
        return addressService.createAddress(addressReqDto);
    }

    @PutMapping("/{address-id}")
    public AddressDto updateAddress(@PathVariable("address-id") String addressId, @RequestBody @Valid AddressReqDto addressReqDto) {
        return addressService.updateAddress(addressId, addressReqDto);
    }

    @DeleteMapping("/{address-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable("address-id") String addressId){
        addressService.deleteAddress(addressId);
    }
}
