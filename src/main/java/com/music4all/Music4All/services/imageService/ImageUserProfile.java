package com.music4all.Music4All.services.imageService;

import com.music4all.Music4All.model.imagesModels.UserImageProfile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageUserProfile {

    UserImageProfile getImageByName(String filename);

    UserImageProfile getImageProfileById(Long idUser);

    Optional<UserImageProfile> saveUserImageProfile(MultipartFile file, Long userId) throws Exception;
}
