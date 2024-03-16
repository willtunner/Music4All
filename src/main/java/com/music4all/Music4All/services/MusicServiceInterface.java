package com.music4all.Music4All.services;

import com.music4all.Music4All.dtos.MusicDTO;
import com.music4all.Music4All.model.Music;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MusicServiceInterface {
    Music createMusic(MusicDTO music, MultipartFile file) throws MessagingException, IOException, InterruptedException;
    Music getMusic(String bandMusic);

    List<Music> getMusicByName(String name);

    List<Music> getMusicsMoreAuditions();
    Boolean deleteMusic(Long idMusic);
    List<Music> getAllMusics();
    Music getMusicById(Long idMusic);
    Music updateMusic(Music music);
}
