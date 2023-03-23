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

    @PostMapping
    public ResponseEntity<Response> saveUser(@RequestBody Disc disc) throws MessagingException, MessagingException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("Saved Disc", discService.saveDisc(disc)))
                        .message("Disco criado")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<Response> getBands() throws InterruptedException {
        //TimeUnit.SECONDS.sleep(3);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("Discs", discService.getDisc()))
                        .message("Discos recuperados")
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
                        .data(Map.of("deleted disc: ", discService.deleteDisc(id)))
                        .message("Disco deletadao!")
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
                        .data(Map.of("Disco by id: ", discService.getDiscById(id)))
                        .message("Disco!")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
}
