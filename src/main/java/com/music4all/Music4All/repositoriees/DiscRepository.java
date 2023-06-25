package com.music4all.Music4All.repositoriees;

import com.music4all.Music4All.model.Band;
import com.music4all.Music4All.model.Disc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiscRepository extends JpaRepository<Disc, Long> {
    Disc findByName(String name);

    List<Disc> findByBandId(Long id);

    @Query(value = "insert into band_discs (band_id, discs_id) values (:bandId,:discId) ", nativeQuery = true)
    Disc saveDiscByBand(Long bandId, Long discId);
}
