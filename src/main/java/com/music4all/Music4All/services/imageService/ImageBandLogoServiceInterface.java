package com.music4all.Music4All.services.imageService;

import com.music4all.Music4All.model.imagesModels.ImageBandLogo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageBandLogoServiceInterface {
    ImageBandLogo getLogo(String filename);

    ImageBandLogo getLogoByIdBand(Long idBand);

    Optional<ImageBandLogo> save(MultipartFile file, Long bandId) throws Exception;
}
