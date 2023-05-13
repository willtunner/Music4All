package com.music4all.Music4All.services.implementations;

import com.music4all.Music4All.model.Band;
import com.music4all.Music4All.model.User;
import com.music4all.Music4All.repositoriees.BandRepository;
import com.music4all.Music4All.repositoriees.UserRepository;
import com.music4all.Music4All.services.BandServiceInterface;
import com.music4all.Music4All.services.imageService.ImageBandLogoSeriveImpl;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BandServiceImpl implements BandServiceInterface {

    private final BandRepository bandRepository;
    private final UserRepository userRepository;
    private final ImageBandLogoSeriveImpl imageBandLogoService;

    @Value("${contato.disco.raiz}")
    private String raiz;

    @Value("${contato.disco.diretorio-fotos}")
    private String diretoriosFotos;


    @Override
    public Band saveBand(Band band) throws MessagingException, IOException {
        return bandRepository.save(band);
    }

    @Override
    public Band getBand(String bandName) {
        return null;
    }

    @Override
    public Band addMember(Long bandId, Long userId) {
        Band band = bandRepository.findById(bandId).orElse(null);
        User member = userRepository.findById(userId).orElse(null);
        band.addMembers(member);
        return bandRepository.save(band);
    }

    @Override
    public Band like(Long bandId, Long userId) {
        Band band = bandRepository.findById(bandId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        band.likeUsers(user);
        return band;
    }

    @Override
    public Band dislike(Long bandId, Long userId) {
        Band band = bandRepository.findById(bandId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        band.dislikeUsers(user);
        return band;
    }

    @Override
    public Band favourite(Long bandId, Long userId) {
        Band band = bandRepository.findById(bandId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        band.favouriteUsers(user);
        return band;
    }

    @Override
    public List<Band> getBandByName(String name) {
        if (name != null) {
            System.out.println("Nome da banda: " + name);
            List<Band> band = bandRepository.findByName(name);
            log.info("Saving new music {} to the database");
            return band;
        }

        return null;
    }

    @Override
    public List<Band> getBandByState(String state) {
        if (state != null) {
            System.out.println("Estado da banda: " + state);
            List<Band> band = bandRepository.findBandByState(state);
            log.info("Listando Bandas por Estado");
            return band;
        }

        return null;
    }

    @Override
    public List<Band> getBandByStateByLimit(String state) {
        return bandRepository.findTop5ByStateOrderByLikeAsc(state);
    }

    @Override
    public Boolean deleteBand(Long idBand) {
        log.info( "Delete Band by id: ", idBand );
        bandRepository.deleteById(idBand);
        return true;
    }

    @Override
    public List<Band> getBands() {
        log.info("List all bands ");
        List<Band> bands = bandRepository.findAll();
        List<Band> bandList = new ArrayList<>();

        bands.forEach((res) -> {
            res.setImage(this.urlImage(res.getId()));
            bandList.add(res);
        });
        return bandList;
    }

    @Override
    public Optional<Band> getBandById(Long idBand) {
        return bandRepository.findById(idBand);
    }

    @Override
    public Band updateBand(Band band) {
        if ( band.getId() != null) {
            log.info("Band {} updated success", band.getName());
            return bandRepository.save(band);
        }
        log.info("Band DON'T update");
        return band;
    }

    public String urlImage(Long idBand) {
        var image = imageBandLogoService.getLogoByIdBand(idBand);
        System.out.println(createImageLink(image.getFilename()));
        return createImageLink(image.getFilename());
    }

    private String createImageLink(String filename) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/band/band-logo/" + filename).toUriString();
    }
}
