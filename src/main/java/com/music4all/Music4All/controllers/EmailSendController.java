package com.music4all.Music4All.controllers;

import com.music4all.Music4All.dtos.EmailDTO;
import com.music4all.Music4All.model.Email;
import com.music4all.Music4All.repositoriees.UserRepository;
import com.music4all.Music4All.services.implementations.EmailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/mail")
public class EmailSendController {

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/send-simple")
    public String sendMail(@RequestParam(value = "file", required = false)MultipartFile[] file, String to, String[] cc, String subject, String body) {
        return emailService.sendMailAttachment(file, to, cc, subject, body);
    }

    @PostMapping("/sending-email")
    public ResponseEntity<Email> sendingEmail(@RequestBody @Valid EmailDTO emailDto) {
        Email emailModel = new Email();
        BeanUtils.copyProperties(emailDto, emailModel);
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }

    @PostMapping("/sending-email-test")
    public ResponseEntity<Email> sendingEmailTest(@RequestBody @Valid EmailDTO emailDto) {
        Email emailModel = new Email();
        BeanUtils.copyProperties(emailDto, emailModel);
        emailService.sendEmailApiTest(emailModel);
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }

}
