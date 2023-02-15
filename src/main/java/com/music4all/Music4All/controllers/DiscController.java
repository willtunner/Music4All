package com.music4all.Music4All.controllers;

import com.music4all.Music4All.model.Disc;
import com.music4all.Music4All.model.User;
import com.music4all.Music4All.model.response.Response;
import com.music4all.Music4All.repositoriees.DiscRepository;
import com.music4all.Music4All.services.implementations.DiscServiceImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/disc")
@CrossOrigin
@RequiredArgsConstructor
public class DiscController {

    private final DiscServiceImpl discService;

    @PostMapping("/save-disc")
    public ResponseEntity<Response> saveUser(@RequestBody Disc disc) throws MessagingException, MessagingException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("Disc", discService.saveDisc(disc)))
                        .message("Disco criado")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }
}
