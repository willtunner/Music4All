package com.music4all.Music4All.services;

import com.music4all.Music4All.dtos.BandDTO;
import com.music4all.Music4All.dtos.bandDtos.BandDotRecord;
import com.music4all.Music4All.model.Band;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BandServiceInterface {

    Band saveBand(BandDotRecord band) throws MessagingException, IOException;
    Band getBand(String bandName);
    Band addMember(Long bandId, Long userId);
    Object like(Long bandId, Long userId);
    Band dislike(Long bandId, Long userId);
    Band favourite(Long bandId, Long userId);
//    Band updateBand(Long id, List<User> users);
    List<Band> getBandByName(String name);
    List<Band> getBandByState(String state);

    List<Band> getBandByStateByLimit(String state);
    Boolean deleteBand(Long idBand);
    List<BandDTO> getBands();
    Optional<Band> getBandById(Long idBand);

    Band updateBand(BandDotRecord bandDto, Long id);
}
