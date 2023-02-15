package com.music4all.Music4All.repositoriees;

import com.music4all.Music4All.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
}
