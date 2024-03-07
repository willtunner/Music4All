package com.music4all.Music4All.services.implementations;

import com.music4all.Music4All.model.Disc;
import com.music4all.Music4All.model.Music;
import com.music4all.Music4All.repositoriees.DiscRepository;
import com.music4all.Music4All.repositoriees.MusicRepository;
import com.music4all.Music4All.services.MusicServiceInterface;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MusicServiceImpl implements MusicServiceInterface {

    private final MusicRepository musicRepository;
    private final DiscRepository discRepository;

    @Override
    public Music createMusic(Music music) throws MessagingException {
        if(music != null) {
            log.info("Saving new music {} to the database", music.getNameMusic());
            Optional<Disc> discOptional = discRepository.findById(music.getDiscId());

            if (discOptional.isPresent()) {
                Disc disc = discOptional.get();

                Music musicSaved = musicRepository.save(music);
                disc.setMusics(Collections.singletonList(musicSaved));
                discRepository.save(disc);
                return musicSaved;
            } else {
                log.info("Disc not found");
                throw  new RuntimeException("Disc not found");
            }

        }
        return null;
    }

    @Override
    public Music getMusic(String nameMusic) {
        return musicRepository.findByNameMusic(nameMusic);
    }

    @Override
    public List<Music> getMusicByName(String name) {
        if (name != null || !name.isEmpty()) {
            List<Music> music = musicRepository.findByName(name);
            log.info("list all musics by name");
            return music;
        }
        return null;
    }

    @Override
    public List<Music> getMusicsMoreAuditions() {
        return musicRepository.findTop5ByOrderByAuditionsDesc();
    }

    @Override
    public Boolean deleteMusic(Long idMusic) {
        log.info( "Delete Music by id: {} ", idMusic );
        musicRepository.deleteById(idMusic);
        return true;
    }

    @Override
    public List<Music> getAllMusics() {
        log.info("List all musics ");
        return musicRepository.findAll();
    }

    @Override
    public Music getMusicById(Long musicId) {

        if (musicId != null) {
            Optional<Music> musicOptional = musicRepository.findById(musicId);
            if (musicOptional.isPresent()) {
                log.info("get music by id {}", musicId);
                return musicOptional.get();
            }
        }
        log.info("Music id is null");
        return null;
    }

    @Override
    public Music updateMusic(Music music) {
        if ( music.getId() != null) {
            log.info("Music {} updated success", music.getNameMusic());
            return musicRepository.save(music);
        }

        log.info("Music DON'T update");
        return music;
    }


}
