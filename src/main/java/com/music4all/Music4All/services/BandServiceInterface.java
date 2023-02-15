package com.music4all.Music4All.services;

import com.music4all.Music4All.model.Band;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Optional;

public interface BandServiceInterface {

    Band saveBand(Band band) throws MessagingException;
    Band getBand(String bandName);
    Boolean deleteBand(Long idBand);
    List<Band> getBands();
    Optional<Band> getBandById(Long idBand);
    Band updateBand(Band band);
}
