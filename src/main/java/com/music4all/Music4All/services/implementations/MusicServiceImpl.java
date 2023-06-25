package com.music4all.Music4All.services.implementations;

import com.music4all.Music4All.model.Music;
import com.music4all.Music4All.repositoriees.MusicRepository;
import com.music4all.Music4All.services.MusicServiceInterface;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MusicServiceImpl implements MusicServiceInterface {

    private final MusicRepository musicRepository;

    @Override
    public Music saveMusic(Music music) throws MessagingException {
        if(music != null) {
            log.info("Saving new music {} to the database", music.getNameMusic());
            return musicRepository.save(music);
        }
        return null;
    }

    @Override
    public Music getMusic(String nameMusic) {
        return musicRepository.findByNameMusic(nameMusic);
    }

    @Override
    public List<Music> getMusicByName(String name) {
        if (name != null) {
            System.out.println("Nome da musica: " + name);
            List<Music> music = musicRepository.findByName(name);
            log.info("Saving new music {} to the database");
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
        log.info( "Delete Music by id: ", idMusic );
        musicRepository.deleteById(idMusic);
        return true;
    }

    @Override
    public List<Music> getAllMusics() {
        log.info("List all musics ");
        return musicRepository.findAll();
    }

    @Override
    public Optional<Music> getMusicById(Long idMusic) {
        return musicRepository.findById(idMusic);
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

    public Music saveMusic(String nameMusic, String duration, MultipartFile file, String lyric, Integer favorite, String description, Integer like, Integer auditions, Long discId) throws IOException {
        Music music = new Music();
        music.setNameMusic(nameMusic);
        music.setMusic(file.getBytes());
        music.setDuration(duration);
        music.setLyric(lyric);
        music.setFavorite(favorite);
        music.setDescription(description);
        music.setLike(like);
        music.setAuditions(auditions);
        music.setDiscId(discId);
        music.setMineType(file.getContentType());
        music.setMusicLink(createImageLink(music.getNameMusic() + "-" + LocalDateTime.now()));
        music.setReleaseDate(LocalDateTime.now());
        return musicRepository.save(music);
    }

    private String createImageLink(String filename) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/music/name-music/" + filename).toUriString();
    }


}
