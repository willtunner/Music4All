package com.music4all.Music4All.controllers;

import com.music4all.Music4All.model.Band;
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
//@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class BandController {
    private final BandServiceImpl bandService;
    private final ImageBandLogoSeriveImpl imageBandLogoService;

    @PostMapping
    public ResponseEntity<Response> saveUser(@RequestBody Band band) throws MessagingException, MessagingException, IOException {

        String downloadURL = "";
        Band bandSaved = bandService.saveBand(band);
        downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("band/image/")
                .path(bandSaved.getId().toString())
                .toUriString();

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("band", bandSaved))
                        .message("Banda criada com sucesso!")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .downloadURL(downloadURL)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<Response> getBands() throws InterruptedException {
        //TimeUnit.SECONDS.sleep(3);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("bands", bandService.getBands()))
                        .message("Todas as bandas listadas")
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
                        .data(Map.of("deleted: ", bandService.deleteBand(id)))
                        .message("Banda deletada!")
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
                        .data(Map.of("listado: ", bandService.getBandByName(name)))
                        .message("Banda por name!")
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
                        .data(Map.of("listado: ", bandService.getBandByStateByLimit(state)))
                        .message("Bandas do seu estado mais escutadas")
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
                        .data(Map.of("listado: ", bandService.getBandByState(state)))
                        .message("Musica listada por estado!")
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
                        .data(Map.of("listado: ", bandService.getBandById(id)))
                        .message("Banda listada por id")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping
    public ResponseEntity<Response> addMusics(@RequestBody Band band) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("listado: ", bandService.updateBand(band)))
                        .message("Banda Atualizada por id")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping("/{bandId}/add/{userId}")
    public ResponseEntity<Response> addMusics(@PathVariable Long bandId, @PathVariable Long userId) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("listado: ", bandService.addMember(bandId, userId )))
                        .message("Membros add")
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
                        .data(Map.of("liked: ", bandService.like(bandId, userId )))
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
                        .data(Map.of("liked: ", bandService.dislike(bandId, userId )))
                        .message("Like User ok!")
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
                        .data(Map.of("liked: ", bandService.favourite(bandId, userId )))
                        .message("Like User ok!")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

//    @PostMapping("/upload-file")
//    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
//
//        System.out.println(file.getOriginalFilename());
//        System.out.println(file.getName());
//        System.out.println(file.getContentType());
//        System.out.println(file.getSize());
//
//        String Path_Directory = new ClassPathResource("static/image").getFile().getAbsolutePath();
//        Files.copy(file.getInputStream(), Paths.get(Path_Directory + File.separator + file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
//
//
//        return "Sucesso";
//    }

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
            log.error("Erro ao salvar imagem", e);
            return SaveResult.builder().error(true).filename(file.getOriginalFilename()).build();
        }

    }

    @GetMapping("/band-logo/{filename}")
    public ResponseEntity<Resource> retrive(@PathVariable String filename) {
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
