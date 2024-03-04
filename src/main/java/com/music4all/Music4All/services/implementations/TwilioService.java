package com.music4all.Music4All.services.implementations;

import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "ACbdd0e1870bf005033f33b974e5b217ed";
    public static final String AUTH_TOKEN = "d65e4c4fbaf2e42f20eeaa3115ca5912";

    public void sendSms() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+5519991748210"),
                        new com.twilio.type.PhoneNumber("+13524780147"),
                        "Will vc é D+")
                .create();

        System.out.println(message.getSid());
    }
}
