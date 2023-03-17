package com.music4all.Music4All.services.implementations;

import com.music4all.Music4All.model.Band;
import com.music4all.Music4All.model.Music;
import com.music4all.Music4All.repositoriees.BandRepository;
import com.music4all.Music4All.repositoriees.UserRepository;
import com.music4all.Music4All.services.BandServiceInterface;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BandServiceImpl implements BandServiceInterface {

    private final BandRepository bandRepository;

    @Override
    public Band saveBand(Band band) throws MessagingException {
        log.info("Saving new band {} to the database", band.getName());
        return bandRepository.save(band);
    }

    @Override
    public Band getBand(String bandName) {
        return null;
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
    public Boolean deleteBand(Long idBand) {
        log.info( "Delete Band by id: ", idBand );
        bandRepository.deleteById(idBand);
        return true;
    }

    @Override
    public List<Band> getBands() {
        log.info("List all bands ");
        return bandRepository.findAll();
    }

    @Override
    public Optional<Band> getBandById(Long idBand) {
        return bandRepository.findById(idBand);
    }

    @Override
    public Band updateBand(Band band) {
        if ( band.getId() != null) {
            log.info("Band {} saved success", band.getName());
            return bandRepository.save(band);
        }
        log.info("Band DON'T update");
        return band;
    }
}
