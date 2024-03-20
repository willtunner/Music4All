package com.music4all.Music4All.controllers;

import com.music4all.Music4All.dtos.userDtos.UserDtoRecord;
import com.music4all.Music4All.model.User;
import com.music4all.Music4All.model.response.Response;
import com.music4all.Music4All.services.imageService.ImageUserProfileServiceImpl;
import com.music4all.Music4All.services.implementations.UserServiceImpl;
import com.music4all.Music4All.utils.FormatNumber;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Controller", description = "Operations related to user management")
public class UserController {

    private final UserServiceImpl userService;

    private  final ImageUserProfileServiceImpl imageUserProfileService;

    @GetMapping
    @Operation(summary = "Get all user details", description = "Gets details of an all user in the music4all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Response> getUsers() throws InterruptedException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("all users", userService.getAllUsers()))
                        .message("Listing all users")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new user", description = "Creates a new user in music4all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Response> createUser(@RequestParam(value = "file", required = false) MultipartFile file,
                                               @RequestParam("name") String name,
                                               @RequestParam("email") String email,
                                               @RequestParam("password") String password,
                                               @RequestParam("cellphone") String cellphone,
                                               @RequestParam("gender") String gender,
                                               @RequestParam("age") Integer age) throws MessagingException, MessagingException {

        String formatedCellphone = FormatNumber.formatPhoneNumber(cellphone);
        User user = new User(name, email, password, formatedCellphone, gender, age);


        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("user created", userService.createUser(user, file)))
                        .message("User created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete an user", description = "Delete an user from the music4all by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
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
    @Operation(summary = "Get user details by id", description = "Gets details of an user in the music4all by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
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
    @Operation(summary = "Get user details by name", description = "Gets details of an user in the music4all by name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
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

    @PutMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update an user", description = "Updates an existing user in the music4all by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Response> updateUser(@RequestParam(value = "file", required = false) MultipartFile file,
                                               @RequestParam("id") Long id,
                                               @RequestParam(value = "name", required = false) String name,
                                               @RequestParam(value = "email", required = false) String email,
                                               @RequestParam(value = "password", required = false) String password,
                                               @RequestParam(value = "cellphone", required = false) String cellphone,
                                               @RequestParam(value = "gender", required = false) String gender,
                                               @RequestParam(value = "age", required = false) Integer age) {

        UserDtoRecord user = new UserDtoRecord(name, email, password, cellphone, gender, age, id);

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("update user by id", userService.updateUser(user, file)))
                        .message("User updated!")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

}
