package com.music4all.Music4All.services;

import com.music4all.Music4All.dtos.JwtAuthenticationResponse;
import com.music4all.Music4All.dtos.RefreshTokenRequest;
import com.music4all.Music4All.dtos.SignUpRequest;
import com.music4all.Music4All.model.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse login(SignUpRequest signUpRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
