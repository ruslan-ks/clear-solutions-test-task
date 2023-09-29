package com.rkostiuk.cstask.dto.mapper;

import com.rkostiuk.cstask.dto.request.NewAddressRequest;
import com.rkostiuk.cstask.dto.response.AddressResponse;
import com.rkostiuk.cstask.entity.Address;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressModelMapper implements AddressMapper {
    private final ModelMapper modelMapper;

    public AddressModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Address toAddress(NewAddressRequest request) {
        return modelMapper.map(request, Address.class);
    }

    @Override
    public AddressResponse toAddressResponse(Address address) {
        return new AddressResponse(address);
    }
}
