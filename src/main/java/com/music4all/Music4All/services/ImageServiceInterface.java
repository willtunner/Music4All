package com.music4all.Music4All.services;

import com.music4all.Music4All.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageServiceInterface {
    Image getImage(String filename);

    Optional<Image> save(MultipartFile file) throws Exception;
}
