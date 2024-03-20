package com.music4all.Music4All.controllers;

import com.music4all.Music4All.model.Disc;
import com.music4all.Music4All.model.response.Response;
import com.music4all.Music4All.services.implementations.DiscServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/disc")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Disc Controller", description = "Operations related to disc management")
public class DiscController {

    private final DiscServiceImpl discService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new disc", description = "Create a new disc in music4all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Disc created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Response> createDisc(@RequestParam(value = "file", required = false) MultipartFile file,
                                               @ModelAttribute Disc disc) throws MessagingException, MessagingException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("disc created", discService.createDisc(disc, file)))
                        .message("Disco criado!")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @GetMapping
    @Operation(summary = "Get all discs details", description = "Gets details of an all discs in the music4all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discs details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Discs not found")
    })
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
    @Operation(summary = "Delete an disc", description = "Delete an disc by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Disc deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Disc not found")
    })
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
    @Operation(summary = "Get disc details by id", description = "Gets details of an disc in the music4all by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disc details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Disc not found")
    })
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
    @Operation(summary = "Get disc details by band", description = "Gets details of an disc in the music4all by band.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Band details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "band not found")
    })
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
