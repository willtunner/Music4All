package com.music4all.Music4All.services.implementations;

import com.music4all.Music4All.model.User;
import com.music4all.Music4All.repositoriees.UserRepository;
import com.music4all.Music4All.services.UserServiceInterface;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return userRepository.findAll();
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
}
