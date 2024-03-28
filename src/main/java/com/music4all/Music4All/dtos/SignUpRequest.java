package com.music4all.Music4All.dtos;

import lombok.Data;

@Data
public class SignUpRequest {

    private String name;
    private String email;
    private String password;
}
