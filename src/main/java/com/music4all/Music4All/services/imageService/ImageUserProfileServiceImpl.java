package com.music4all.Music4All.services.imageService;

import com.music4all.Music4All.exceptions.ImageNotFoundException;
import com.music4all.Music4All.model.User;
import com.music4all.Music4All.model.imagesModels.UserImageProfile;
import com.music4all.Music4All.repositoriees.UserRepository;
import com.music4all.Music4All.repositoriees.imageRepository.UserProfileImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageUserProfileServiceImpl implements  ImageUserProfile{
    private final UserProfileImageRepository userProfileImageRepository;
    private final UserRepository userRepository;

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

        //Verifica se o usuário já tem imagem
        //ImageBandLogo imageBandLogo = bandImageLogoRepository.findByBandId(bandId);
        UserImageProfile imageProfile = userProfileImageRepository.findImageProfileById(userId);

        if (imageProfile != null) {
            imageProfile.setFilename(file.getOriginalFilename()  + LocalDateTime.now());
            imageProfile.setData(file.getBytes());
            imageProfile.setMineType(file.getContentType());
            imageProfile.setUserId(userId);
            //Salvar link
            imageProfile.setLink(createImageLink(imageProfile.getFilename()));

            UserImageProfile image = Optional.of(userProfileImageRepository.save(imageProfile)).orElseThrow();

            // atualizar o user image com id da image
            User user = userRepository.findById(userId).orElseThrow();
            user.setImageProfileId(image.getId());
            userRepository.save(user);

            return Optional.of(image);

        } else {
            UserImageProfile imageSaved = new UserImageProfile();
            imageSaved.setFilename(file.getOriginalFilename() + LocalDateTime.now());
            imageSaved.setData(file.getBytes());
            imageSaved.setMineType(file.getContentType());
            imageSaved.setUserId(userId);
            //Salvar link
            imageSaved.setLink(createImageLink(imageSaved.getFilename()));

            UserImageProfile image = Optional.of(userProfileImageRepository.save(imageSaved)).orElseThrow();

            // atualizar o user image com id da image
            User user = userRepository.findById(userId).orElseThrow();
            user.setImageProfileId(image.getId());
            userRepository.save(user);

            return Optional.of(image);
        }


    }

    private String createImageLink(String filename) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/user/image-profile/" + filename).toUriString();
    }
}
