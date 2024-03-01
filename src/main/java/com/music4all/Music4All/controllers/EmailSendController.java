package com.music4all.Music4All.controllers;

import com.music4all.Music4All.dtos.EmailDto;
import com.music4all.Music4All.model.Band;
import com.music4all.Music4All.model.Email;
import com.music4all.Music4All.model.User;
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
        return emailService.sendMail(file, to, cc, subject, body);
    }

    @PostMapping("/sending-email")
    public ResponseEntity<Email> sendingEmail(@RequestBody @Valid EmailDto emailDto) {
        Email emailModel = new Email();
        BeanUtils.copyProperties(emailDto, emailModel);
        User user = userRepository.findById(1L).get();
//        emailService.sendEmailApi2(user);
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }

    @PostMapping("/sending-email-test")
    public ResponseEntity<Email> sendingEmailTest(@RequestBody @Valid EmailDto emailDto) {
        Email emailModel = new Email();
        BeanUtils.copyProperties(emailDto, emailModel);
        emailService.sendEmailApi(emailModel);
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }

}
