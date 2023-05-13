package com.music4all.Music4All.services.implementations;

import com.music4all.Music4All.model.User;
import com.music4all.Music4All.model.imagesModels.ImageBandLogo;
import com.music4all.Music4All.model.imagesModels.UserImageProfile;
import com.music4all.Music4All.repositoriees.UserRepository;
import com.music4all.Music4All.repositoriees.imageRepository.UserProfileImageRepository;
import com.music4all.Music4All.services.UserServiceInterface;
import com.music4all.Music4All.services.imageService.ImageUserProfileServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserServiceInterface {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ImageUserProfileServiceImpl imageUserProfileService;


    @Override
    public User saveUser(User user) throws MessagingException {
        log.info("Saving new user {} to the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Override
    public Boolean deleteUser(Long idUser) {
        log.info( "Delete User by id: ", idUser );
        userRepository.deleteById(idUser);
        return true;
    }

    @Override
    public List<User> getUsers(String name) {
        if (name != null) {
            System.out.println("Nome do usuário: " + name);
            List<User> user = userRepository.findByName(name);
//            log.info("Saving new user {} to the database");
            return user;
        }

        return null;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("List all users ");
        List<User> users = userRepository.findAll();
        List<User> usersList = new ArrayList<>();

        users.forEach((res) -> {
            res.setImage(this.urlImage(res.getId()));
            usersList.add(res);
        });
        return usersList;
    }


    @Override
    public Optional<User> getUserById(Long idUser) {
        return userRepository.findById(idUser);
    }

    @Override
    public User updateUser(User user) {
        if ( user.getId() != null) {
            log.info("User {} saved success", user.getName());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        log.info("User DON'T update");
        return user;
    }

    public String urlImage(Long userId) {

        if(imageUserProfileService.getImageProfileById(userId) != null) {
            var image = imageUserProfileService.getImageProfileById(userId);
            return createImageLink(image.getFilename());
        }
        return null;

    }

    private String createImageLink(String filename) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/user/image-profile/" + filename).toUriString();
    }

}
