package com.music4all.Music4All.dtos;

import com.music4all.Music4All.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    List<User> toUser(List<UserDTO> userDTOs);

    List<UserDTO> toUserDTO(List<User> users);
}
