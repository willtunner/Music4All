package com.music4all.Music4All.services.imageService;

import com.music4all.Music4All.exceptions.ImageNotFoundException;
import com.music4all.Music4All.model.Band;
import com.music4all.Music4All.model.imagesModels.ImageBandLogo;
import com.music4all.Music4All.repositoriees.BandRepository;
import com.music4all.Music4All.repositoriees.imageRepository.BandImageLogoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageBandLogoSeriveImpl implements ImageBandLogoServiceInterface {

    private final BandImageLogoRepository bandImageLogoRepository;
    private final BandRepository bandRepository;


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

        // Verifica se tem uma imagem para essa banda
        ImageBandLogo imageBandLogo = bandImageLogoRepository.findByBandId(bandId);

        if (imageBandLogo != null) {
            imageBandLogo.setFilename(file.getOriginalFilename() + LocalDateTime.now());
            imageBandLogo.setData(file.getBytes());
            imageBandLogo.setMineType(file.getContentType());
            //Salvar link
            imageBandLogo.setLink(createImageLink(imageBandLogo.getFilename()));

            // Atualiza a imagem na entidade  Banda
            Band band = bandRepository.findById(bandId).orElseThrow(null);
            band.setLogoBandId(imageBandLogo.getId());
            bandRepository.save(band);
            return Optional.of(bandImageLogoRepository.save(imageBandLogo));

        } else {
            //Cria Imagem
            ImageBandLogo newImage = new ImageBandLogo();
            //Seta os dados
            newImage.setFilename(file.getOriginalFilename() + LocalDateTime.now());
            newImage.setData(file.getBytes());
            newImage.setMineType(file.getContentType());
            newImage.setBandId(bandId);
            //Salvar link
            newImage.setLink(createImageLink(newImage.getFilename()));
            //Salva e armazena na variavel
            ImageBandLogo image = Optional.of(bandImageLogoRepository.save(newImage)).orElseThrow();
            //Procura a banda
            Band band = bandRepository.findById(bandId).orElseThrow(null);
            band.setLogoBandId(image.getId());
            bandRepository.save(band);
            return Optional.of(bandImageLogoRepository.save(newImage));
        }

    }

    private String createImageLink(String filename) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/band/band-logo/" + filename).toUriString();
    }
}
