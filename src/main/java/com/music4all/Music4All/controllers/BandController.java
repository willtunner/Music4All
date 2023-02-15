package com.music4all.Music4All.controllers;

import com.music4all.Music4All.model.Band;
import com.music4all.Music4All.model.response.Response;
import com.music4all.Music4All.services.implementations.BandServiceImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/band")
@CrossOrigin
@RequiredArgsConstructor
public class BandController {
    private final BandServiceImpl bandService;

    @PostMapping("/save-band")
    public ResponseEntity<Response> saveUser(@RequestBody Band band) throws MessagingException, MessagingException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("band", bandService.saveBand(band)))
                        .message("Banda criada com sucesso!")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @GetMapping("/bands")
    public ResponseEntity<Response> getBands() throws InterruptedException {
        //TimeUnit.SECONDS.sleep(3);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("bands", bandService.getBands()))
                        .message("Bandas recuperadas")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("deleted: ", bandService.deleteBand(id)))
                        .message("Banda deletada!")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/band-by-id/{id}")
    public ResponseEntity<Response> findUserById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("listado: ", bandService.getBandById(id)))
                        .message("Banda listada!")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
}
