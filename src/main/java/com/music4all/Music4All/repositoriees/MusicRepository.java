package com.music4all.Music4All.repositoriees;

import com.music4all.Music4All.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Long> {

    Music findByNameMusic(String nameMusic);

    @Query(value = "select * from music where name_music ilike :name% ", nativeQuery = true)
    List<Music> findByName(@Param("name") String name);

    List<Music> findTop5ByOrderByAuditionsDesc();
}
