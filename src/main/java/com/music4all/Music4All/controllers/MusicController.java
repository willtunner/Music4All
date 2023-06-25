package com.music4all.Music4All.controllers;

import com.music4all.Music4All.dtos.MusicDTO;
import com.music4all.Music4All.model.Music;
import com.music4all.Music4All.model.response.Response;
import com.music4all.Music4All.services.implementations.MusicServiceImpl;
import jakarta.mail.MessagingException;
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

    @GetMapping
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

    @PostMapping("add-music")
    public MusicDTO music(
            @RequestParam("nameMusic") String nameMusic,
            @RequestParam("duration") String duration,
            @RequestParam("file") MultipartFile file,
            @RequestParam("lyric") String lyric,
            @RequestParam("favorite") Integer favorite,
            @RequestParam("description") String description,
            @RequestParam("like") Integer like,
            @RequestParam("auditions") Integer auditions,
            @RequestParam("discId") Long discId) throws IOException {

        Music music = musicService.saveMusic(nameMusic, duration, file, lyric, favorite, description, like, auditions, discId);
        MusicDTO musicDto = new MusicDTO();
        musicDto.setId(music.getId());
        musicDto.setNameMusic(music.getNameMusic());
        musicDto.setReleaseDate(music.getReleaseDate());
        musicDto.setAuditions(music.getAuditions());
        musicDto.setDeleted(music.getDeleted());
        musicDto.setCreated(music.getCreated());
        musicDto.setDescription(music.getDescription());
        musicDto.setDislike(music.getDislike());
        musicDto.setLike(music.getLike());
        musicDto.setFavorite(music.getFavorite());
        musicDto.setMineType(music.getMineType());
        musicDto.setDuration(music.getDuration());
        musicDto.setMusicLink(music.getMusicLink());
        musicDto.setDiscId(music.getDiscId());
        musicDto.setMembersByMusic(music.getMembersByMusic());
        musicDto.setLyric(music.getLyric());
        return musicDto;
    }

    @GetMapping("/name-music/{filename}")
    public ResponseEntity<Resource> retrive(@PathVariable String musicName) {
        var music = musicService.getMusic(musicName);
        var body = new ByteArrayResource(music.getMusic());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, music.getMineType())
                .body(body);
    }
}
