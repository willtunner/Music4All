package com.music4all.Music4All.controllers;

import com.music4all.Music4All.services.implementations.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send-message")
public class TwilioController {

    @Autowired
    private TwilioService twilioService;

    public TwilioController(TwilioService twilioService) {
        this.twilioService = twilioService;
    }

    @GetMapping("/send-sms")
    public void sendSms() {
        this.twilioService.sendSms();;
    }
}
