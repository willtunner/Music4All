package com.music4all.Music4All.repositoriees;

import com.music4all.Music4All.model.LikeByMusic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkeByMusicRepository extends JpaRepository<LikeByMusic, Long> {
}
