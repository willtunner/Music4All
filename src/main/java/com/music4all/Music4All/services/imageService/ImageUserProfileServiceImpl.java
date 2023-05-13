package com.music4all.Music4All.services.imageService;

import com.music4all.Music4All.exceptions.ImageNotFoundException;
import com.music4all.Music4All.model.imagesModels.UserImageProfile;
import com.music4all.Music4All.repositoriees.imageRepository.UserProfileImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageUserProfileServiceImpl implements  ImageUserProfile{
    private final UserProfileImageRepository userProfileImageRepository;

    @Override
    public UserImageProfile getImageByName(String filename) {
        return userProfileImageRepository.findByFilename(filename).orElseThrow(ImageNotFoundException::new);
    }

    @Override
    public UserImageProfile getImageProfileById(Long idUser) {
        return userProfileImageRepository.findImageProfileById(idUser);
    }

    @Override
    public Optional<UserImageProfile> saveUserImageProfile(MultipartFile file, Long userId) throws Exception {
        if(userProfileImageRepository.existsByFilename(file.getOriginalFilename())) {
            log.info("Image {} have already existed", file.getOriginalFilename());
            Optional<UserImageProfile> image = userProfileImageRepository.findByFilename(file.getOriginalFilename());
            return image;
        }

        UserImageProfile imageSaved = new UserImageProfile();
        imageSaved.setFilename(file.getOriginalFilename());
        imageSaved.setData(file.getBytes());
        imageSaved.setMineType(file.getContentType());
        imageSaved.setUserId(userId);

        return Optional.of(userProfileImageRepository.save(imageSaved));
    }
}
