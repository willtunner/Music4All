package com.music4all.Music4All.controllers;

import com.music4all.Music4All.model.Music;
import com.music4all.Music4All.model.response.Response;
import com.music4all.Music4All.services.implementations.MusicServiceImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/music")
@CrossOrigin
@RequiredArgsConstructor
public class MusicController {

    private final MusicServiceImpl musicService;

    @PostMapping
    public ResponseEntity<Response> saveUser(@RequestBody Music music) throws MessagingException, MessagingException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("Music", musicService.saveMusic(music)))
                        .message("Musica criada com sucesso!")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @GetMapping("/musics")
    public ResponseEntity<Response> getMusics() throws InterruptedException {
        //TimeUnit.SECONDS.sleep(3);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("Musics", musicService.getAllMusics()))
                        .message("Listando todas as musicas")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> findMusicById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("listado: ", musicService.getMusicById(id)))
                        .message("Musica Id listado!")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/more-auditions")
    public ResponseEntity<Response> moreAuditions() {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("listado: ", musicService.getMusicsMoreAuditions()))
                        .message("Musica mais escutadas")
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
                        .data(Map.of("listado: ", musicService.getMusicByName(name)))
                        .message("Musica por name listado!")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<Response> updateUser(@PathVariable("id") Long id, @RequestBody Music music) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("update: ", musicService.updateMusic(music)))
                        .message("Musica atualizada!")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
}
