package com.rkostiuk.cstask.dto.mapper;

import com.rkostiuk.cstask.dto.request.NewUserRequest;
import com.rkostiuk.cstask.dto.response.UserWithAddressResponse;
import com.rkostiuk.cstask.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserModelMapper implements UserMapper {
    private final ModelMapper modelMapper;

    public UserModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public User toUser(NewUserRequest newUserRequest) {
        return modelMapper.map(newUserRequest, User.class);
    }

    @Override
    public UserWithAddressResponse toUserAddressResponse(User user) {
        return new UserWithAddressResponse(user, user.getAddress());
    }
}
