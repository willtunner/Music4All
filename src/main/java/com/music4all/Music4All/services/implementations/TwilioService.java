package com.music4all.Music4All.services.implementations;

import com.music4all.Music4All.utils.FormatNumber;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {
    @Value("${twilio.accountSid}")
    private String accountSid;
    @Value("${twilio.authToken}")
    private String authToken;
    @Value("${twilio.phoneNumber}")
    private String phoneNumberTwilio;

    public void sendSms(String textSms, String number) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(FormatNumber.formatPhoneNumber(number)),
                        new com.twilio.type.PhoneNumber(phoneNumberTwilio),
                        textSms)
                .create();

        System.out.println(message.getSid());
    }

    public void smsCreateUser(String phoneNumber, String userName) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(phoneNumber),
                        new com.twilio.type.PhoneNumber(phoneNumberTwilio),
                        "Olá "+ userName +", bem-vindo ao Music4All! Estamos felizes em tê-lo(a) conosco. " +
                                "Esperamos que desfrute de nossa plataforma e encontre a música perfeita para cada momento.")
                .create();

        System.out.println(message.getSid());
    }
}
