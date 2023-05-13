package com.music4all.Music4All.controllers;

import com.music4all.Music4All.model.Image;
import com.music4all.Music4All.model.response.SaveResult;
import com.music4all.Music4All.services.implementations.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
@Slf4j
public class ImageController {

    @Autowired
    private ImageServiceImpl imageService;

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> retrive(@PathVariable String filename) {
        var image = imageService.getImage(filename);
        var body = new ByteArrayResource(image.getData());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, image.getMineType())
                .body(body);
    }

    @PostMapping
    public SaveResult upload(@RequestPart MultipartFile file) throws Exception {

        try {
            Optional<Image> image = imageService.save(file);
            return SaveResult.builder()
                    .error(false)
                    .filename(file.getOriginalFilename())
                    .link(createImageLink(file.getOriginalFilename()))
                    .build();

        } catch (Exception e) {
            log.error("Erro ao salvar imagem", e);
            return SaveResult.builder().error(true).filename(file.getOriginalFilename()).build();
        }

    }

    @PostMapping("/multiples")
    public List<SaveResult> uploadMulti(@RequestPart List<MultipartFile> files) {
        return files.stream()
                .map((res) -> {
                    try {
                        return this.upload(res);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    private String createImageLink(String filename) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/image/"+ filename).toUriString();
    }
}
