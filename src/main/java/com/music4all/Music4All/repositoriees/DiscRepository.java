package com.music4all.Music4All.repositoriees;

import com.music4all.Music4All.model.Disc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscRepository extends JpaRepository<Disc, Long> {
}
