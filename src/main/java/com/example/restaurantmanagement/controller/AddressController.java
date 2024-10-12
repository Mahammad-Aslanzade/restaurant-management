package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.address.AddressDetailDto;
import com.example.restaurantmanagement.model.address.AddressReqDto;
import com.example.restaurantmanagement.model.address.AddressDto;
import com.example.restaurantmanagement.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public List<AddressDto> getAllAddreses() {
        return addressService.getAllAddreses();
    }

    @GetMapping("/currentUser")
    public List<AddressDetailDto> getUserAddress(){
        return addressService.getAddresesByUser();
    }

    @GetMapping("/{addressId}")
    public AddressDto getAddressById(@PathVariable String addressId) {
        return addressService.getAddressById(addressId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDto createAddress(@RequestBody @Valid AddressReqDto addressReqDto) {
        return addressService.createAddress(addressReqDto);
    }

    @PutMapping("/{addressId}")
    public AddressDto updateAddress(@PathVariable String addressId, @RequestBody @Valid AddressReqDto addressReqDto) {
        return addressService.updateAddress(addressId, addressReqDto);
    }

    @DeleteMapping("/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable String addressId){
        addressService.deleteAddress(addressId);
    }
}
