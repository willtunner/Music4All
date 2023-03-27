package com.music4all.Music4All.repositoriees;

import com.music4all.Music4All.model.Disc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscRepository extends JpaRepository<Disc, Long> {
    Disc findByName(String name);

    List<Disc> findByBandId(Long id);
}
