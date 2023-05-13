package com.music4all.Music4All.services;

import com.music4all.Music4All.model.Band;
import com.music4all.Music4All.model.User;
import com.music4all.Music4All.model.imagesModels.ImageBandLogo;
import com.music4all.Music4All.model.imagesModels.UserImageProfile;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    User saveUser(User user) throws MessagingException;
    /*
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
     */
//    User getUser(String username);
    Boolean deleteUser(Long idUser);

    List<User> getUsers(String name);
    List<User>getAllUsers();
    Optional<User> getUserById(Long idUser);
    User updateUser(User user);

}
