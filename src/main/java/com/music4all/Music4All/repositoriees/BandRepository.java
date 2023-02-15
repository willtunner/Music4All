package com.music4all.Music4All.repositoriees;

import com.music4all.Music4All.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandRepository extends JpaRepository<Band, Long> {
}
