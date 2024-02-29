package com.music4all.Music4All.services;

import org.springframework.web.multipart.MultipartFile;

public interface EmailServiceInterface {
    String sendMail(MultipartFile[] file, String to, String[] cc, String subject, String body);
}
