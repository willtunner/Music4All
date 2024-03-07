package com.music4all.Music4All.controllers;

import com.music4all.Music4All.dtos.bandDtos.BandDotRecord;
import com.music4all.Music4All.model.imagesModels.ImageBandLogo;
import com.music4all.Music4All.model.response.Response;
import com.music4all.Music4All.model.response.SaveResult;
import com.music4all.Music4All.services.implementations.BandServiceImpl;
import com.music4all.Music4All.services.imageService.ImageBandLogoSeriveImpl;
import jakarta.mail.MessagingException;
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
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/band")
@RequiredArgsConstructor
@Slf4j
public class BandController {
    private final BandServiceImpl bandService;
    private final ImageBandLogoSeriveImpl imageBandLogoService;

    @PostMapping
    public ResponseEntity<Response> createBand(@RequestBody BandDotRecord band) throws MessagingException, MessagingException, IOException {



        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("band created", bandService.createBand(band)))
                        .message("Band created success!")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<Response> getAllBands() throws InterruptedException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("list all bands", bandService.getBands()))
                        .message("Listed All bands")
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
                        .data(Map.of("deleted band", bandService.deleteBand(id)))
                        .message("Deleted Band")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @GetMapping("list/{name}")
    public ResponseEntity<Response> findBandByName(@PathVariable("name") String name) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("listing by name", bandService.getBandByName(name)))
                        .message("Listing Band by name")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/more-auditions-by-state/{state}")
    public ResponseEntity<Response> moreAuditionsByState(@PathVariable("state")  String state) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("listing more auditions by state", bandService.getBandByStateByLimit(state)))
                        .message("Listing more auditions by state")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("list/state/{state}")
    public ResponseEntity<Response> findBandByState(@PathVariable("state") String state) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("listing by state", bandService.getBandByState(state)))
                        .message("Listing Band by state")
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
                        .data(Map.of("listed by id", bandService.getBandById(id)))
                        .message("List Band by id")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<Response> addMusics(@PathVariable("id") Long id, @RequestBody BandDotRecord bandDto) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("updated band", bandService.updateBand(bandDto, id)))
                        .message("Updated band by id")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping("/{bandId}/add/{userId}")
    public ResponseEntity<Response> addMember(@PathVariable Long bandId, @PathVariable Long userId) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("add member", bandService.addMember(bandId, userId )))
                        .message("Added a new member to the Band")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping("/{bandId}/like/{userId}")
    public ResponseEntity<Response> likeBand(@PathVariable Long bandId, @PathVariable Long userId) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("band liked", bandService.like(bandId, userId )))
                        .message("Like User ok!")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping("/{bandId}/dislike/{userId}")
    public ResponseEntity<Response> dislike(@PathVariable Long bandId, @PathVariable Long userId) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("disliked", bandService.dislike(bandId, userId )))
                        .message("Dislike user ok!")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping("/{bandId}/favourite/{userId}")
    public ResponseEntity<Response> favouriteBand(@PathVariable Long bandId, @PathVariable Long userId) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("liked by user", bandService.favourite(bandId, userId )))
                        .message("Like User ok!")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping("/{bandId}/save-logo")
    public SaveResult upload(@RequestPart MultipartFile file, @PathVariable Long bandId) throws Exception {

        try {
            Optional<ImageBandLogo> image = imageBandLogoService.save(file, bandId);
            return SaveResult.builder()
                    .error(false)
                    .filename(file.getOriginalFilename())
                    .link(createImageLink(file.getOriginalFilename()))
                    .idBand(bandId)
                    .build();

        } catch (Exception e) {
            log.error("Error saving image", e);
            return SaveResult.builder().error(true).filename(file.getOriginalFilename()).build();
        }

    }

    @GetMapping("/band-logo/{filename}")
    public ResponseEntity<Resource> recoverImage(@PathVariable String filename) {
        var image = imageBandLogoService.getLogo(filename);
        var body = new ByteArrayResource(image.getData());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, image.getMineType())
                .body(body);
    }

    @GetMapping("/{idBand}/logo")
    public String urlImage(@PathVariable Long idBand) {
        var image = imageBandLogoService.getLogoByIdBand(idBand);
        System.out.println(createImageLink(image.getFilename()));
        return createImageLink(image.getFilename());
    }

    private String createImageLink(String filename) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/band/band-logo/" + filename).toUriString();
    }
}
