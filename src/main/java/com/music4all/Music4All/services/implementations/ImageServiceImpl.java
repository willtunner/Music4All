package com.music4all.Music4All.services.implementations;

import com.music4all.Music4All.exceptions.ImageNotFoundException;
import com.music4all.Music4All.model.Image;
import com.music4all.Music4All.repositoriees.ImageRepository;
import com.music4all.Music4All.services.ImageServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageServiceInterface {

    private final ImageRepository imageRepository;
    @Override
    public Image getImage(String filename) {
        return imageRepository.findByFilename(filename).orElseThrow(ImageNotFoundException::new);
    }

    @Override
    public Optional<Image> save(MultipartFile file) throws Exception {
        if(imageRepository.existsByFilename(file.getOriginalFilename())) {
            log.info("Image {} have already existed", file.getOriginalFilename());
            Optional<Image> image = imageRepository.findByFilename(file.getOriginalFilename());
            return image;
        }

        Image imageSaved = new Image();
        imageSaved.setFilename(file.getOriginalFilename());
        imageSaved.setData(file.getBytes());
        imageSaved.setMineType(file.getContentType());

        return Optional.of(imageRepository.save(imageSaved));
    }


}
