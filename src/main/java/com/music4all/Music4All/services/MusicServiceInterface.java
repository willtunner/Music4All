package com.music4all.Music4All.services;

import com.music4all.Music4All.model.Music;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Optional;

public interface MusicServiceInterface {
    Music saveMusic(Music music) throws MessagingException;
    Music getMusic(String bandMusic);

    List<Music> getMusicByName(String name);

    List<Music> getMusicsMoreAuditions();
    Boolean deleteMusic(Long idMusic);
    List<Music> getAllMusics();
    Optional<Music> getMusicById(Long idMusic);
    Music updateMusic(Music music);
}
