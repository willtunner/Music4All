package com.music4all.Music4All.controllers;

import com.music4all.Music4All.services.implementations.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/mail")
public class EmailSendController {

    @Autowired
    private EmailServiceImpl emailService;

    @PostMapping("/send")
    public String sendMail(@RequestParam(value = "file", required = false)MultipartFile[] file, String to, String[] cc, String subject, String body) {
        return emailService.sendMail(file, to, cc, subject, body);
    }
}
