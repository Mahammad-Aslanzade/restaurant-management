package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.AddressEntity;
import com.example.restaurantmanagement.dao.entity.UserEntity;
import com.example.restaurantmanagement.dao.repository.jpa.AddressRepository;
import com.example.restaurantmanagement.enums.Role;
import com.example.restaurantmanagement.exceptions.InvalidException;
import com.example.restaurantmanagement.exceptions.NotAllowedException;
import com.example.restaurantmanagement.exceptions.NotFoundException;
import com.example.restaurantmanagement.mapper.AddressMapper;
import com.example.restaurantmanagement.model.address.AddressReqDto;
import com.example.restaurantmanagement.model.address.AddressDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final MyUserDetailService userDetailService;
    private final UserService userService;

    public List<AddressDto> getAllAddreses() {
        log.info("ACTION.getAllAddreses.start");
        List<AddressEntity> addressEntityList = addressRepository.findAll();
        List<AddressDto> addressDtoList = addressMapper.listToDto(addressEntityList);
        log.info("ACTION.getAllAddreses.end");
        return addressDtoList;
    }

    private AddressEntity getAddressEntity(String addressId) {
        return addressRepository.findById(addressId).orElseThrow(() ->
                new NotFoundException(
                        "ADDRESS_NOT_FOUND",
                        String.format("ACTION.ERROR.getAddressEntity addressId : %s", addressId)
                )
        );
    }

    public AddressDto getAddressById(String addressId) {
        log.info("ACTION.getAddressById.start addressId : {}", addressId);
        AddressEntity addressEntity = getAddressEntity(addressId);
        AddressDto addressDto = addressMapper.mapToDto(addressEntity);
        log.info("ACTION.getAddressById.end addressId : {}", addressId);
        return addressDto;
    }

    public List<AddressDto> getAddresesByUser(String userId) {
        log.info("ACTION.getAddresesByUser.start userId : {}", userId);
        List<AddressEntity> addressEntityList = addressRepository.findAllByUserId(userId);
        List<AddressDto> addressDtoList = addressMapper.listToDto(addressEntityList);
        log.info("ACTION.getAddresesByUser.end userId : {}", userId);
        return addressDtoList;
    }

    public AddressDto createAddress(AddressReqDto addressReqDto) {
        log.info("ACTION.createAddress.start |  reqBody : {}", addressReqDto);
        AddressEntity addressEntity = addressMapper.mapToEntity(addressReqDto);

        // Define user and Check this user has permission to create address for particular userId
        UserEntity user;
        String userId = addressReqDto.getUserId();
        UserEntity client = userDetailService.getCurrentAuthenticatedUser();
        // Validate field userId valid->ADMIN | notValid->USER
        if (addressReqDto.getUserId() != null && client.getRole() != Role.ADMIN) {
            throw new InvalidException(
                    "userId", "USER_ID_FIELD_NOT_ALLOWED",
                    String.format("ACTION.ERROR.updateAddress client : %s | reqBody : %s", client.getId(), addressReqDto)
            );
        }

        if (client.getRole() == Role.ADMIN) {
            user = userService.getUserEntity(userId);
        } else {
            user = client;
        }
        //

        addressEntity.setUser(user);
        addressRepository.save(addressEntity);
        log.info("ACTION.createAddress.end |  reqBody : {}", addressReqDto);
        return addressMapper.mapToDto(addressEntity);
    }


    public AddressDto updateAddress(String addressId, AddressReqDto addressReqDto) {
        log.info("ACTION.updateAddress.start addressId : {} | addressReqBody : {}", addressId, addressReqDto);
        AddressEntity oldAddress = getAddressEntity(addressId);
        AddressEntity updatedAddress = addressMapper.mapToEntity(addressReqDto);
        updatedAddress.setId(oldAddress.getId());

        UserEntity client = userDetailService.getCurrentAuthenticatedUser();

        if (addressReqDto.getUserId() != null && client.getRole() != Role.ADMIN) {
            throw new InvalidException(
                    "userId", "USER_ID_FIELD_NOT_ALLOWED",
                    String.format("ACTION.ERROR.updateAddress client : %s | reqBody : %s", client.getId(), addressReqDto)
            );
        }

        // Can't update this address  !ADMIN || !CLIENT'S_ADDRESS
        if (!(client.getRole() == Role.ADMIN || client.getId().equals(oldAddress.getUser().getId()))) {
            throw new NotAllowedException(
                    "You are not allowed to update address this address",
                    String.format("ACTION.ERROR.updateAddress reqBody : %s | currentUserId : %s ", addressReqDto, client.getId())
            );
        }

        UserEntity user;
        if (client.getRole() == Role.ADMIN) {
            user = userService.getUserEntity(addressReqDto.getUserId());
        } else {
            user = client;
        }

        updatedAddress.setUser(user);
        addressRepository.save(updatedAddress);

        log.info("ACTION.updateAddress.end addressId : {} | addressReqBody : {}", addressId, addressReqDto);
        return addressMapper.mapToDto(updatedAddress);
    }

    public void deleteAddress(String addressId){
        log.info("ACTION.deleteAddress.start addressId: {}", addressId);
        AddressEntity address = getAddressEntity(addressId);
        addressRepository.delete(address);
        log.info("ACTION.deleteAddress.end addressId: {}", addressId);
    }


}
