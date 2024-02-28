package com.music4all.Music4All.repositoriees;

import com.music4all.Music4All.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BandRepository extends JpaRepository<Band, Long> {

    @Query(value = "select * from band where name ilike :name% ", nativeQuery = true)
    List<Band> findByName(@Param("name") String name);

    @Query(value = "SELECT EXISTS ( SELECT 1 FROM like_band lb WHERE lb.band_id = :bandId AND lb.user_id = :userId ) AS exists_like; ", nativeQuery = true)
    Boolean findLikeUserBand(Long bandId, Long userId);

    @Transactional
    @Modifying
    @Query(value = "delete from like_band lb where lb.band_id = :bandId and lb.user_id = :userId ", nativeQuery = true)
    void dislikeUserBand(Long bandId, Long userId);

    @Transactional
    @Modifying
    @Query(value = "insert into like_band values (:bandId, :userId)", nativeQuery = true)
    void userLikeBand(Long bandId, Long userId);

    List<Band> findBandByState(String state);

    List<Band> findTop5ByStateOrderByLikeAsc(String state);

}
