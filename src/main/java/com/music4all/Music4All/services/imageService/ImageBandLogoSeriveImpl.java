package com.music4all.Music4All.services.imageService;

import com.music4all.Music4All.exceptions.ImageNotFoundException;
import com.music4all.Music4All.model.imagesModels.ImageBandLogo;
import com.music4all.Music4All.repositoriees.imageRepository.BandImageLogoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageBandLogoSeriveImpl implements ImageBandLogoServiceInterface {

    private final BandImageLogoRepository bandImageLogoRepository;


    @Override
    public ImageBandLogo getLogo(String filename) {
        return bandImageLogoRepository.findByFilename(filename).orElseThrow(ImageNotFoundException::new);
    }

    @Override
    public ImageBandLogo getLogoByIdBand(Long idBand) {
        return bandImageLogoRepository.findByBandId(idBand);
    }

    @Override
    public Optional<ImageBandLogo> save(MultipartFile file, Long bandId) throws Exception {
        if(bandImageLogoRepository.existsByFilename(file.getOriginalFilename())) {
            log.info("Image {} have already existed", file.getOriginalFilename());
            Optional<ImageBandLogo> image = bandImageLogoRepository.findByFilename(file.getOriginalFilename());
            return image;
        }

        ImageBandLogo imageSaved = new ImageBandLogo();
        imageSaved.setFilename(file.getOriginalFilename());
        imageSaved.setData(file.getBytes());
        imageSaved.setMineType(file.getContentType());
        imageSaved.setBandId(bandId);

        return Optional.of(bandImageLogoRepository.save(imageSaved));
    }
}
