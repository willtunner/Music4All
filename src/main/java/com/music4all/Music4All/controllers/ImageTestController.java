package com.music4all.Music4All.controllers;

import com.music4all.Music4All.model.User;
import com.music4all.Music4All.model.response.Response;
import com.music4all.Music4All.repositoriees.imageRepository.ImageTestRepository;
import com.music4all.Music4All.services.implementations.UserTestService;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image-test")
@Slf4j
public class ImageTestController {

    private final UserTestService userTestService;
    private  final ImageTestRepository imageTestRepository;

    @PostMapping
    public ResponseEntity<Response> saveImageTest(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) throws MessagingException, MessagingException, IOException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("user", userTestService.saveUserTest(file, name, email, password)))
                        .downloadURL(createImageLink(file.getOriginalFilename()))
                        .message("Usuário criado")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    private String createImageLink(String filename) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/image-test/" + filename).toUriString();
    }

//    @GetMapping("/image-test/{filename}")
//    public ResponseEntity<Resource> retrive(@PathVariable String filename) {
//        var image = imageTestRepository.findByNa
//        var body = new ByteArrayResource(image.getData());
//
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, image.getMineType())
//                .body(body);
//    }
}
