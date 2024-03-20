package com.music4all.Music4All.controllers;

import com.music4all.Music4All.services.implementations.TwilioService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/send-message")
public class TwilioController {

    @Autowired
    private TwilioService twilioService;

    public TwilioController(TwilioService twilioService) {
        this.twilioService = twilioService;
    }

    @PostMapping("/send-sms")
    public void sendSms(@RequestParam  String messageBody, @RequestParam String phoneNumber) {
        this.twilioService.sendSms(messageBody, phoneNumber);
    }

}
