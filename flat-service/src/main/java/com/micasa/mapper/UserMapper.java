package com.micasa.mapper;

import com.micasa.dto.UserDto;
import com.micasa.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper
{
    User userDtoToUser(UserDto userDto);
    UserDto userToUserDto(User user);
}
