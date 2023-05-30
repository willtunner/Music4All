package com.music4all.Music4All.services;

import com.music4all.Music4All.model.ImageTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserTestServiceInterface {

    ImageTest saveUserTest (MultipartFile file, String name, String email, String password) throws IOException;
}
