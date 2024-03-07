package com.music4all.Music4All.services.implementations;

import com.music4all.Music4All.dtos.BandDTO;
import com.music4all.Music4All.dtos.bandDtos.BandDotRecord;
import com.music4all.Music4All.enun.EmailType;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BandServiceImpl implements BandServiceInterface {

    private final BandRepository bandRepository;
    private final UserRepository userRepository;
    private final ImageBandLogoSeriveImpl imageBandLogoService;
    @Autowired
    private EmailServiceImpl emailService;

    private final ImageUserProfileServiceImpl imageUserProfileService;

    @Override
    public Band createBand(BandDotRecord band) throws MessagingException, IOException {
        Band newBand = new Band();
        newBand.setName(band.name());
        newBand.setCity(band.city());
        newBand.setCountry(band.country());
        newBand.setGenre(band.genre());
        newBand.setState(band.state());
        newBand.setCreatorId(band.creatorId());
        bandRepository.save(newBand);
        this.emailService.saveEmail(newBand.getId(), EmailType.CREATE_BAND);
        return newBand;
    }

    @Override
    public Band getBand(String bandName) {
        return null;
    }

    @Override
    public Band addMember(Long bandId, Long userId) {
        Band band = bandRepository.findById(bandId).orElse(null);
        User member = userRepository.findById(userId).orElse(null);
        if (band != null && member != null) {
            band.addMembers(member);
            log.info("User {} added to band", member.getName());
            return bandRepository.save(band);
        }
        return null;
    }

    @Override
    public Object like(Long bandId, Long userId) {
        Map<String, Object> keyValue = new HashMap<>();
        Optional<Band> bandOptional = bandRepository.findById(bandId);
        if (bandOptional.isPresent()) {
            Boolean checkExistUserLikeBand = bandRepository.findLikeUserBand(bandId, userId);
            if (checkExistUserLikeBand) {
                bandRepository.dislikeUserBand(bandId, userId);
                keyValue.put("liked", false);
            } else {
                bandRepository.userLikeBand(bandId, userId);
                keyValue.put("liked", true);
            }
            return keyValue;
        }
        return null;
    }

    @Override
    public Band dislike(Long bandId, Long userId) {
        Band band = bandRepository.findById(bandId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (band != null && user != null) band.dislikeUsers(user);

        return band;
    }

    @Override
    public Band favourite(Long bandId, Long userId) {
        Band band = bandRepository.findById(bandId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if ( band != null && user != null ) {
            band.favouriteUsers(user);
            log.info("User {} favorited the band", user.getName());
            return band;
        }
        return null;
    }

    @Override
    public List<Band> getBandByName(String name) {
        if (name != null) {
            List<Band> bands = bandRepository.findByName(name);
            log.info("Get bands by name {}", bands);
            return bands;
        }
        return null;
    }

    @Override
    public List<Band> getBandByState(String state) {
        if (state != null) {
            List<Band> bands = bandRepository.findBandByState(state);
            log.info("Get bands by state {}", bands);
            return bands;
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

        log.info("List all bands ");
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
        return createImageLink(image.getFilename());
    }

    private String createImageLink(String filename) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/band/band-logo/" + filename).toUriString();
    }
}
