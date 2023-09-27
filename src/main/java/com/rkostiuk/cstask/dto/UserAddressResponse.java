package com.rkostiuk.cstask.dto;

import com.rkostiuk.cstask.entity.UserAddress;

public record UserAddressResponse(String city, String street, int building, Integer apartment) {
    public UserAddressResponse(UserAddress address) {
        this(address.getCity(), address.getStreet(), address.getBuilding(), address.getApartment());
    }
}
