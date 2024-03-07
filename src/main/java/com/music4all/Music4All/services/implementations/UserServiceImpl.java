package com.music4all.Music4All.services.implementations;

import com.music4all.Music4All.dtos.UserDTO;
import com.music4all.Music4All.dtos.userDtos.UserDtoRecord;
import com.music4all.Music4All.enun.EmailType;
import com.music4all.Music4All.model.User;
import com.music4all.Music4All.repositoriees.UserRepository;
import com.music4all.Music4All.services.UserServiceInterface;
import com.music4all.Music4All.services.imageService.ImageUserProfileServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    @Autowired
    private EmailServiceImpl emailService;


    @Override
    public User createUser(User user) throws MessagingException {
        log.info("Saving new user {} to the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        emailService.saveEmail(user.getId(), EmailType.CREATE_USER);
        return user;
    }


    @Override
    public Boolean deleteUser(Long idUser) {
       if (idUser != null) {
           log.info( "Delete User by id: {}", idUser );
           userRepository.deleteById(idUser);
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
    public UserDTO getUserById(Long idUser) {
        UserDTO userDTO = new UserDTO();
        User user = userRepository.findById(idUser).orElseThrow();

        userDTO.setId(user.getId());
        userDTO.setNome(user.getName());
        userDTO.setAge(user.getAge());
        userDTO.setCellphone(user.getCellphone());

        if (user.getImage() == null) {
            userDTO.setLinkImageProfile("https://www.pngall.com/wp-content/uploads/5/User-Profile-PNG-High-Quality-Image.png");
        } else {
            userDTO.setLinkImageProfile(user.getImage().getLink());
        }

        userDTO.setBands(user.getBands());
        userDTO.setFollowing(user.getFollowing());
        userDTO.setFollowers(user.getFollowers());
        userDTO.setGender(user.getGender());
        userDTO.setEmail(user.getEmail());
        userDTO.setCreated(user.getCreated());
        return userDTO;
    }

    @Override
    public User updateUser(UserDtoRecord userDto, Long id) {
        if ( userDto.name() != null ) {
            Optional<User> user = userRepository.findById(id);

            if(user.isPresent()) {
                log.info("User {} saved success", userDto.name());
                if (!userDto.password().isEmpty()) user.get().setPassword(passwordEncoder.encode(userDto.password()));
                if (!userDto.name().isEmpty()) user.get().setName(userDto.name());
                if (!userDto.email().isEmpty()) user.get().setEmail(userDto.email());
                if (!userDto.cellphone().isEmpty()) user.get().setCellphone(userDto.cellphone());
                if (!userDto.gender().isEmpty()) user.get().setGender(userDto.gender());
                if (userDto.age() != null && userDto.age() > 0) user.get().setAge(userDto.age());
                return userRepository.save(user.get());
            } else {
                return null;
            }
        } else {
            log.info("User DON'T update");
            return null;
        }

    }

}
