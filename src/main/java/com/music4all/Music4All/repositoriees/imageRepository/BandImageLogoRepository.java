package com.music4all.Music4All.repositoriees.imageRepository;

import com.music4all.Music4All.model.imagesModels.ImageBandLogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BandImageLogoRepository extends JpaRepository<ImageBandLogo, Long> {
    Optional<ImageBandLogo> findByFilename(String name);

    @Query(value = "select * from image_band_logo where band_id = :idBand order by created desc limit 1", nativeQuery = true)
    ImageBandLogo findByBandId(Long idBand);

    boolean existsByFilename(String filename);
}
