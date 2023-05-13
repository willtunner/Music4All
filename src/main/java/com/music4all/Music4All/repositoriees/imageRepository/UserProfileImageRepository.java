package com.music4all.Music4All.repositoriees.imageRepository;

import com.music4all.Music4All.model.imagesModels.ImageBandLogo;
import com.music4all.Music4All.model.imagesModels.UserImageProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserProfileImageRepository extends JpaRepository<UserImageProfile, Long> {
    Optional<UserImageProfile> findByFilename(String name);

    @Query(value = "select * from user_image_profile where user_id = :userId order by created desc limit 1", nativeQuery = true)
    UserImageProfile findImageProfileById(Long userId);

    boolean existsByFilename(String filename);
}
