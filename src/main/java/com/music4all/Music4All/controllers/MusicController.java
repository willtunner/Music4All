package com.music4all.Music4All.controllers;

import com.music4all.Music4All.dtos.MusicDTO;
import com.music4all.Music4All.model.Music;
import com.music4all.Music4All.model.response.Response;
import com.music4all.Music4All.services.implementations.MusicServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@Tag(name = "Music Controller", description = "Operations related to music")
public class MusicController {

    private final MusicServiceImpl musicService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new music", description = "Creates a new music in music4all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Response> createMusic(@Parameter(description = "Music file", required = false) @RequestPart(value = "file", required = false) MultipartFile file,
                                                @RequestParam("nameMusic") String nameMusic,
                                                @RequestParam("lyric") String lyric,
                                                @RequestParam("description") String description,
                                                @RequestParam("discId") Long discId) throws IOException, InterruptedException {
        MusicDTO music = new MusicDTO();
        music.setNameMusic(nameMusic);
        music.setLyric(lyric);
        music.setDescription(description);
        music.setDiscId(discId);

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
    @Operation(summary = "Get all musics details", description = "Gets details of an all musics in the music4all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Music details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Music not found")
    })
    public ResponseEntity<Response> getAllMusics() throws InterruptedException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("musics", musicService.getAllMusics()))
                        .message("listing all Musics")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("{id}")
    @Operation(summary = "Get music details by id", description = "Gets details of an music in the music4all by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Music details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Music not found")
    })
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
    @Operation(summary = "Get music more auditions", description = "Return top 5 musics!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Music details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Music not found")
    })
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
    @Operation(summary = "Get music by name", description = "Get music by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Music details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Music not found")
    })
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
    @Operation(summary = "Update music by id", description = "Update music")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Music details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Music not found")
    })
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

//    @PostMapping("add-music")
//    public ResponseEntity<Response> music(@RequestBody MusicDTO musicDTO) throws IOException {
//        return ResponseEntity.ok(
//                Response.builder()
//                        .timeStamp(LocalDateTime.now())
//                        .data(Map.of("add_music", MusicMapper.INSTANCE.toMusic(musicDTO)))
//                        .message("Add Music")
//                        .status(HttpStatus.OK)
//                        .statusCode(HttpStatus.OK.value())
//                        .build()
//        );
//    }
//
//    @GetMapping("/name-music/{filename}")
//    public ResponseEntity<Resource> retrive(@PathVariable String musicName) {
//        var music = musicService.getMusic(musicName);
//        var body = new ByteArrayResource(music.getMusic());
//
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, music.getMineType())
//                .body(body);
//    }
}
