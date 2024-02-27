package com.music4all.Music4All.services;

import com.music4all.Music4All.dtos.UserDTO;
import com.music4all.Music4All.dtos.userDtos.UserDtoRecord;
import com.music4all.Music4All.model.User;
import jakarta.mail.MessagingException;

import java.util.List;

public interface UserServiceInterface {
    User saveUser(User user) throws MessagingException;

    Boolean deleteUser(Long idUser);

    List<User> getUsers(String name);
    List<UserDTO>getAllUsers();
    UserDTO getUserById(Long idUser);
    User updateUser(UserDtoRecord user, Long id);

}
