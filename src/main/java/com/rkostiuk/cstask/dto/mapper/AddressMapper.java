package com.rkostiuk.cstask.dto.mapper;

import com.rkostiuk.cstask.dto.request.NewAddressRequest;
import com.rkostiuk.cstask.dto.response.AddressResponse;
import com.rkostiuk.cstask.entity.Address;

public interface AddressMapper {
    Address toAddress(NewAddressRequest request);
    AddressResponse toAddressResponse(Address address);
}
