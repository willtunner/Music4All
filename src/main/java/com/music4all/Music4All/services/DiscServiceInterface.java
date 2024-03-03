package com.music4all.Music4All.services;

import com.music4all.Music4All.model.Disc;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Optional;

public interface DiscServiceInterface {

    Disc createDisc(Disc disc) throws MessagingException;
    Disc getDisc(String disc);
    Boolean deleteDisc(Long idDisc);

    List<Disc> getDiscsByBand(Long id);
    List<Disc> getDisc();
    Optional<Disc> getDiscById(Long idDisc);
    Disc updateBand(Disc disc);
}
