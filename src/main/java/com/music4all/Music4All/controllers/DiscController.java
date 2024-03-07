package com.music4all.Music4All.controllers;

import com.music4all.Music4All.model.Disc;
import com.music4all.Music4All.model.response.Response;
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

    @PostMapping
    public ResponseEntity<Response> createDisc(@RequestBody Disc disc) throws MessagingException, MessagingException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("disc created", discService.createDisc(disc)))
                        .message("Disco criado!")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<Response> getDiscs() throws InterruptedException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("list all discs", discService.getDisc()))
                        .message("List all discs")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("disc deleted: ", discService.deleteDisc(id)))
                        .message("Disc deleted")
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
                        .data(Map.of("find disc by id", discService.getDiscById(id)))
                        .message("Find disc by id!")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("band/{id}")
    public ResponseEntity<Response> findDiscByBand(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("disc by id", discService.getDiscsByBand(id)))
                        .message("Find disc by id")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
}
