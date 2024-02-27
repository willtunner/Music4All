package com.music4all.Music4All.services.implementations;

import com.music4all.Music4All.dtos.BandDTO;
import com.music4all.Music4All.dtos.bandDtos.BandDotRecord;
import com.music4all.Music4All.model.Band;
import com.music4all.Music4All.model.User;
import com.music4all.Music4All.repositoriees.BandRepository;
import com.music4all.Music4All.repositoriees.UserRepository;
import com.music4all.Music4All.services.BandServiceInterface;
import com.music4all.Music4All.services.imageService.ImageBandLogoSeriveImpl;
import com.music4all.Music4All.services.imageService.ImageUserProfileServiceImpl;
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

    private final ImageUserProfileServiceImpl imageUserProfileService;

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
    public List<BandDTO> getBands() {
        log.info("List all bands ");
        List<Band> bands = bandRepository.findAll();
        List<BandDTO> bandList = new ArrayList<>();

        bands.forEach((band) -> {
            BandDTO bandDTO = new BandDTO();

            bandDTO.setId(band.getId());
            bandDTO.setName(band.getName());
            bandDTO.setGenre(band.getGenre());
            bandDTO.setCountry(band.getCountry());
            bandDTO.setCity(band.getCity());
            bandDTO.setState(band.getState());
            bandDTO.setCreatorId(band.getCreatorId());
            if (band.getLogo() == null) {
                bandDTO.setLogo("https://cdn-icons-png.flaticon.com/512/2748/2748558.png");
            } else {
                bandDTO.setLogo(band.getLogo().getLink());
            }

            bandDTO.setCreated(band.getCreated());
            bandDTO.setLike(band.getLike());
            bandDTO.setDislike(band.getDislike());
            bandDTO.setFavourite(band.getFavourite());
            bandDTO.setDiscs(band.getDiscs());
            bandDTO.setMembers(band.getMembers());

            bandList.add(bandDTO);
        });
        return bandList;
    }

    @Override
    public Optional<Band> getBandById(Long idBand) {
        return bandRepository.findById(idBand);
    }

    @Override
    public Band updateBand(BandDotRecord bandDto, Long id) {
        if ( id != null) {
            Optional<Band> bandOptional = bandRepository.findById(id);
            if (bandOptional.isPresent()) {
                Band bandSave = bandOptional.get();

                //todo: Fazer tratamento para somente os menbros da banda atualizar a própria banda

                if (bandDto.name() != null && !bandDto.name().isEmpty()) bandSave.setName(bandDto.name());
                if (bandDto.state() != null && !bandDto.state().isEmpty()) bandSave.setState(bandDto.state());
                if (bandDto.country() != null && !bandDto.country().isEmpty()) bandSave.setCountry(bandDto.country());
                if (bandDto.city() != null && !bandDto.city().isEmpty()) bandSave.setCity(bandDto.city());
                if (bandDto.genre() != null && !bandDto.genre().isEmpty()) bandSave.setGenre(bandDto.genre());

                log.info("Band {} updated success", bandDto.name());
                return bandRepository.save(bandSave);
            } else {
                log.info("Band DON'T update");
                return null;
            }
        } else {
            log.info("Band DON'T update");
            return null;
        }


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
