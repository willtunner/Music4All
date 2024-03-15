package com.music4all.Music4All.services;

import com.music4all.Music4All.dtos.UserDTO;
import com.music4all.Music4All.dtos.userDtos.UserDtoRecord;
import com.music4all.Music4All.model.User;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserServiceInterface {
    User createUser(User user, MultipartFile file) throws MessagingException;

    Boolean deleteUser(Long idUser);

    List<User> getUsers(String name);
    List<User>getAllUsers();
    UserDTO getUserById(Long idUser);
    User updateUser(UserDtoRecord user, Long id);

}
