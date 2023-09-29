package com.rkostiuk.cstask.dto.response;

import com.rkostiuk.cstask.entity.Address;

public record AddressResponse(String city, String street, int building, Integer apartment) {
    public AddressResponse(Address address) {
        this(address.getCity(), address.getStreet(), address.getBuilding(), address.getApartment());
    }
}
