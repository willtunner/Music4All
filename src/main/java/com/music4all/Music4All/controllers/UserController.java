package com.music4all.Music4All.controllers;

import com.music4all.Music4All.dtos.userDtos.UserDtoRecord;
import com.music4all.Music4All.model.User;
import com.music4all.Music4All.model.imagesModels.UserImageProfile;
import com.music4all.Music4All.model.response.Response;
import com.music4all.Music4All.model.response.SaveResult;
import com.music4all.Music4All.services.imageService.ImageUserProfileServiceImpl;
import com.music4all.Music4All.services.implementations.UserServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceImpl userService;

    private  final ImageUserProfileServiceImpl imageUserProfileService;

    @GetMapping
    public ResponseEntity<Response> getUsers() throws InterruptedException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("users", userService.getAllUsers()))
                        .message("Usuários recuperados")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<Response> createUser(@RequestBody @Valid User user) throws MessagingException, MessagingException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("user created", userService.createUser(user)))
                        .message("User created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("user deleted by id", userService.deleteUser(id)))
                        .message("User deleted!")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> findUserById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("user listed by id: ", userService.getUserById(id)))
                        .message("User listed")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("list/{name}")
    public ResponseEntity<Response> findMusicByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("find user by name", userService.getUsers(name)))
                        .message("Find user by name")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<Response> updateUser(@PathVariable("id") Long id, @RequestBody  @Valid UserDtoRecord user) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("update user by id", userService.updateUser(user, id)))
                        .message("User updated!")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping("/{userId}/save-image-profile")
    public SaveResult upload(@RequestPart MultipartFile file, @PathVariable Long userId) throws Exception {

        try {
            Optional<UserImageProfile> image = imageUserProfileService.saveUserImageProfile(file, userId);
            return SaveResult.builder()
                    .error(false)
                    .filename(image.get().getFilename())
                    .link(image.get().getLink())
                    .idBand(userId)
                    .build();

        } catch (Exception e) {
            log.error("Error saving image profile", e);
            return SaveResult.builder().error(true).filename(file.getOriginalFilename()).build();
        }

    }

    @GetMapping("/image-profile/{filename}")
    public ResponseEntity<Resource> retrive(@PathVariable String filename) {
        var image = imageUserProfileService.getImageByName(filename);
        var body = new ByteArrayResource(image.getData());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, image.getMineType())
                .body(body);
    }

}
