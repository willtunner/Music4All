package com.music4all.Music4All.repositoriees;

import com.music4all.Music4All.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByFilename(String name);

    boolean existsByFilename(String filename);


}
