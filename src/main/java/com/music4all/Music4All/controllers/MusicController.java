package com.music4all.Music4All.controllers;

import com.music4all.Music4All.dtos.MusicDTO;
import com.music4all.Music4All.dtos.MusicMapper;
import com.music4all.Music4All.model.Music;
import com.music4all.Music4All.model.response.Response;
import com.music4all.Music4All.services.implementations.MusicServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/music")
@CrossOrigin
@RequiredArgsConstructor
public class MusicController {

    private final MusicServiceImpl musicService;

    @PostMapping
    public ResponseEntity<Response> createMusic(@RequestParam(value = "file", required = false) MultipartFile file,
                                             @ModelAttribute MusicDTO music) throws IOException, InterruptedException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("music", musicService.createMusic(music, file)))
                        .message("Music saved successfully!")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<Response> getAllMusics() throws InterruptedException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("all_musics", musicService.getAllMusics()))
                        .message("listing all Musics")
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
                        .data(Map.of("music_by_id", musicService.getMusicById(id)))
                        .message("Music listed by id")
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
                        .data(Map.of("more_audictions", musicService.getMusicsMoreAuditions()))
                        .message("Musics more listens")
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
                        .data(Map.of("listed_by_name", musicService.getMusicByName(name)))
                        .message("Listed by name")
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
                        .data(Map.of("music_updated", musicService.updateMusic(music)))
                        .message("Music Updated")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping("add-music")
    public ResponseEntity<Response> music(@RequestBody MusicDTO musicDTO) throws IOException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("add_music", MusicMapper.INSTANCE.toMusic(musicDTO)))
                        .message("Add Music")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/name-music/{filename}")
    public ResponseEntity<Resource> retrive(@PathVariable String musicName) {
        var music = musicService.getMusic(musicName);
        var body = new ByteArrayResource(music.getMusic());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, music.getMineType())
                .body(body);
    }
}
