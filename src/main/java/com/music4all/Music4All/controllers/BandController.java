package com.music4all.Music4All.controllers;

import com.music4all.Music4All.dtos.bandDtos.BandDtoRecord;
import com.music4all.Music4All.model.response.Response;
import com.music4all.Music4All.services.implementations.BandServiceImpl;
import com.music4all.Music4All.services.imageService.ImageBandLogoSeriveImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/band")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Band Controller", description = "Operations related to Band management")
public class BandController {
    private final BandServiceImpl bandService;
    private final ImageBandLogoSeriveImpl imageBandLogoService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new band", description = "Creates a new band in music4all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Band created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Response> createBand(@RequestParam(value = "file", required = false) MultipartFile file,
                                               @ModelAttribute BandDtoRecord band) throws MessagingException, MessagingException, IOException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("band_created", bandService.createBand(band, file)))
                        .message("Band created success!")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @GetMapping
    @Operation(summary = "Get all bands details", description = "Gets details of an all bands in the music4all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bands details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Bands not found")
    })
    public ResponseEntity<Response> getAllBands() throws InterruptedException {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("bands", bandService.getBands()))
                        .message("Listed All bands")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete an band", description = "Delete an band from the music4all by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Band deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Band not found")
    })
    public ResponseEntity<Response> deleteUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("deleted_band", bandService.deleteBand(id)))
                        .message("Deleted Band")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @GetMapping("list/{name}")
    @Operation(summary = "Get band details by name", description = "Gets details of an band in the music4all by name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Band details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Band not found")
    })
    public ResponseEntity<Response> findBandByName(@PathVariable("name") String name) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("listing_by_name", bandService.getBandByName(name)))
                        .message("Listing Band by name")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/more-auditions-by-state/{state}")
    @Operation(summary = "List bands more auditions", description = "list 5 more auditions of an bands in the music4all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Response> moreAuditionsByState(@PathVariable("state")  String state) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("auditions", bandService.getBandByStateByLimit(state)))
                        .message("Listing more auditions by state")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("list/state/{state}")
    @Operation(summary = "List bands by state", description = "List bands by state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Response> findBandByState(@PathVariable("state") String state) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("listing_by_state", bandService.getBandByState(state)))
                        .message("Listing Band by state")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("{id}")
    @Operation(summary = "Get band details by id", description = "Gets details of an band in the music4all by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Band details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Band not found")
    })
    public ResponseEntity<Response> findUserById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("listed_by_id", bandService.getBandById(id)))
                        .message("List Band by id")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update an band", description = "Updates an existing band in the music4all by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Band updated successfully"),
            @ApiResponse(responseCode = "404", description = "Band not found")
    })
    public ResponseEntity<Response> addMusics(@RequestParam(value = "file", required = false) MultipartFile file,
                                              @ModelAttribute BandDtoRecord bandDto) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("updated_band", bandService.updateBand(bandDto, file)))
                        .message("Updated band by id")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping(value = "/{bandId}/add/{userId}")
    @Operation(summary = "Add members to band", description = "Add members in the band")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member add successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
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
    @Operation(summary = "User like a band", description = "User like a band")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member add successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
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
    @Operation(summary = "User dislike a band", description = "User dislike a band")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member add successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
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
    @Operation(summary = "User favourite a band", description = "User favourite a band")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorite band successfully"),
            @ApiResponse(responseCode = "404", description = "Don't favorite a band")
    })
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

//    @PostMapping("/{bandId}/save-logo")
//    public SaveResult upload(@RequestPart MultipartFile file, @PathVariable Long bandId) throws Exception {
//
//        try {
//            Optional<ImageBandLogo> image = imageBandLogoService.save(file, bandId);
//            return SaveResult.builder()
//                    .error(false)
//                    .filename(file.getOriginalFilename())
//                    .link(createImageLink(file.getOriginalFilename()))
//                    .idBand(bandId)
//                    .build();
//
//        } catch (Exception e) {
//            log.error("Error saving image", e);
//            return SaveResult.builder().error(true).filename(file.getOriginalFilename()).build();
//        }
//
//    }
//
//    @GetMapping("/band-logo/{filename}")
//    public ResponseEntity<Resource> recoverImage(@PathVariable String filename) {
//        var image = imageBandLogoService.getLogo(filename);
//        var body = new ByteArrayResource(image.getData());
//
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, image.getMineType())
//                .body(body);
//    }
//
//    @GetMapping("/{idBand}/logo")
//    public String urlImage(@PathVariable Long idBand) {
//        var image = imageBandLogoService.getLogoByIdBand(idBand);
//        System.out.println(createImageLink(image.getFilename()));
//        return createImageLink(image.getFilename());
//    }
//
//    private String createImageLink(String filename) {
//        return ServletUriComponentsBuilder.fromCurrentRequest()
//                .replacePath("/band/band-logo/" + filename).toUriString();
//    }
}
