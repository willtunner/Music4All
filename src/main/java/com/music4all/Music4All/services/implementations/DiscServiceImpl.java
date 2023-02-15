package com.music4all.Music4All.services.implementations;

import com.music4all.Music4All.model.Disc;
import com.music4all.Music4All.repositoriees.DiscRepository;
import com.music4all.Music4All.services.DiscServiceInterface;
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
public class DiscServiceImpl implements DiscServiceInterface {

    private final DiscRepository disckRepository;

    @Override
    public Disc saveDisc(Disc disc) throws MessagingException {
        log.info("Saving new Disc {} to the database", disc.getName());
        return disckRepository.save(disc);
    }

    @Override
    public Disc getDisc(String disc) {
        return null;
    }

    @Override
    public Boolean deleteDisc(Long idDisc) {
        return null;
    }

    @Override
    public List<Disc> getDisc() {
        return null;
    }

    @Override
    public Optional<Disc> getDiscById(Long idDisc) {
        return Optional.empty();
    }

    @Override
    public Disc updateBand(Disc disc) {
        return null;
    }
}
