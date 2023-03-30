package com.music4all.Music4All.controllers;

import com.music4all.Music4All.model.Attachment;
import com.music4all.Music4All.model.Band;
import com.music4all.Music4All.model.response.Response;
import com.music4all.Music4All.services.implementations.BandServiceImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/band")
//@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class BandController {
    private final BandServiceImpl bandService;

    @PostMapping
    public ResponseEntity<Response> saveUser(@RequestBody Band band, @RequestParam("file") MultipartFile file) throws MessagingException, MessagingException, IOException {

        String downloadURL = "";
        Band bandSaved = bandService.saveBand(band, file);
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

    @PutMapping("/{bandId}/members/{memberId}")
    public ResponseEntity<Response> addMusics(@PathVariable Long bandId, @PathVariable Long memberId) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("listado: ", bandService.addMember(bandId, memberId )))
                        .message("Membros add")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @PostMapping("/upload-file")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {

        System.out.println(file.getOriginalFilename());
        System.out.println(file.getName());
        System.out.println(file.getContentType());
        System.out.println(file.getSize());

//        String Path_Directory = "C:\\Users\\William\\Documents\\Estudos\\SpringBoot\\Music4All\\src\\main\\resources\\image";
        String Path_Directory = new ClassPathResource("static/image").getFile().getAbsolutePath();
        Files.copy(file.getInputStream(), Paths.get(Path_Directory + File.separator + file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);


        return "Sucesso";
    }
}
