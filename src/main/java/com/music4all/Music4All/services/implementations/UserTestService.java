package com.music4all.Music4All.services.implementations;

import com.music4all.Music4All.model.ImageTest;
import com.music4all.Music4All.repositoriees.imageRepository.ImageTestRepository;
import com.music4all.Music4All.services.UserTestServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserTestService implements UserTestServiceInterface {

    private  final ImageTestRepository imageTestRepository;
    @Override
    public ImageTest saveUserTest(MultipartFile file, String name, String email, String password) throws IOException {

        ImageTest imageTest = new ImageTest();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if(fileName.contains(".."))
        {
            System.out.println("Arquivo não é valido");
        }

        try{
            imageTest.setImage(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageTest.setName(name);
        imageTest.setEmail(email);
        imageTest.setPassword(password);

        return imageTestRepository.save(imageTest);
    }

}
