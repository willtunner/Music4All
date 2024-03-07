package com.music4all.Music4All.dtos;

public record EmailDTO(String ownerRef, String emailFrom, String emailTo, String subject, String text) {
}
