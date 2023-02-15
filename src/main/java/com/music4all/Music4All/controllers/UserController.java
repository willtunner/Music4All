package com.music4all.Music4All.controllers;

import com.music4all.Music4All.model.User;
import com.music4all.Music4All.model.response.Response;
import com.music4all.Music4All.services.implementations.UserServiceImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/users")
    public ResponseEntity<Response> getUsers() throws InterruptedException {
        //TimeUnit.SECONDS.sleep(3);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("users", userService.getUsers()))
                        .message("Usuários recuperados")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping("/save-user")
    public ResponseEntity<Response> saveUser(@RequestBody User user) throws MessagingException, MessagingException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("user", userService.saveUser(user)))
                        .message("Usuário criado")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("deleted: ", userService.deleteUser(id)))
                        .message("Usuário deletado!")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/user-by-id/{id}")
    public ResponseEntity<Response> findUserById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("listado: ", userService.getUserById(id)))
                        .message("Usuário listado!")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("update: ", userService.updateUser(user)))
                        .message("Usuário atualizado!")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
}
