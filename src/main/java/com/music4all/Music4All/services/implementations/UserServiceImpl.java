package com.music4all.Music4All.services.implementations;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.music4all.Music4All.dtos.userDtos.UserDtoRecord;
import com.music4all.Music4All.enun.EmailType;
import com.music4all.Music4All.enun.Role;
import com.music4all.Music4All.model.User;
import com.music4all.Music4All.repositoriees.UserRepository;
import com.music4all.Music4All.services.UserServiceInterface;
import com.music4all.Music4All.services.imageService.ImageUserProfileServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    private final StorageService storageService;
    private final TwilioService twilioService;
    @Autowired
    private AmazonS3 s3Client;
    @Autowired
    private EmailServiceImpl emailService;
    String bucketName = "imageusers";

    @Override
    public User createUser(User user, MultipartFile file) throws MessagingException {

        String urlImage = storageService.saveImageS3(bucketName, file);

        if (urlImage != null) user.setProfileImageUrl(urlImage);
        if (!user.getCellphone().isEmpty()) twilioService.smsCreateUser(user.getCellphone(), user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        emailService.saveEmail(user.getId(), EmailType.CREATE_USER);

        log.info("Saving new user {} to the database", user.getName());
        return user;
    }


    @Override
    public Boolean deleteUser(Long idUser) {
       if (idUser != null) {
           log.info( "Delete User by id: {}", idUser );
           userRepository.deletedTrueUser(idUser);
           return true;
       } else {
           return null;
       }
    }

    @Override
    public List<User> getUsers(String name) {
        if (name != null) {
            List<User> user = userRepository.findByName(name);
            log.info( "Find users by search nane");
            return user;
        }

        return null;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("List all users ");
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long idUser) {
        Optional<User> user = userRepository.findById(idUser);

        if (user.isPresent()) {
            return user.get();
        } else {
            log.info("User DON'T find");
            return null;
        }


    }

    @Override
    public User updateUser(UserDtoRecord userDto, MultipartFile file) {
        if ( userDto.id() != null ) {
            Optional<User> user = userRepository.findById(userDto.id());

            if(user.isPresent()) {
                if (file != null && !file.isEmpty()) {
                    if (!file.getContentType().startsWith("image/")) {
                        throw new IllegalArgumentException("The file sent is not an image");
                    }

                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    File fileObject = storageService.convertMultiPartFileToFile(file);
                    s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObject));
                    String urlImage = storageService.getFileUrl(fileName, bucketName);
                    user.get().setProfileImageUrl(urlImage);
                    fileObject.delete();
                    System.out.println("URL: " + urlImage);
                }

                if (userDto.password() != null) user.get().setPassword(passwordEncoder.encode(userDto.password()));
                if (userDto.name() != null) user.get().setName(userDto.name());
                if (userDto.email() != null) user.get().setEmail(userDto.email());
                if (userDto.cellphone() != null) user.get().setCellphone(userDto.cellphone());
                if (userDto.gender() != null) user.get().setGender(userDto.gender());
                if (userDto.age() != null && userDto.age() > 0) user.get().setAge(userDto.age());

                log.info("User {} saved success", userDto.name());
                return userRepository.save(user.get());
            } else {
                log.info("User DON'T Exist!");
                return null;
            }
        } else {
            log.info("User DON'T update");
            return null;
        }

    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

}
