package com.music4all.Music4All.utils;

public class FormatNumber {

    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 11) {
            return "+55" + phoneNumber;
        } else {
            return phoneNumber;
        }
    }
}
