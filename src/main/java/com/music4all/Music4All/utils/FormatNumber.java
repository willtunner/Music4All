package com.music4all.Music4All.utils;

public class FormatNumber {

    public static String formatPhoneNumber(String phoneNumber) {
        // Verifica se o número de telefone tem o tamanho esperado
        if (phoneNumber.length() == 11) {
            // Adiciona "+55" ao início do número de telefone
            return "+55" + phoneNumber;
        } else {
            // Retorna o número de telefone original
            return phoneNumber;
        }
    }
}
