package com.ecommerceproject.UserService.Mapper;

import com.ecommerceproject.UserService.Dto.UserDto;
import com.ecommerceproject.UserService.Model.User;

public class UserEntityDtoMapper {

    public static UserDto getUserDtoFromUserEntity(User user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());

        return userDto;
    }
}
