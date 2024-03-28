package com.music4all.Music4All.controllers;

import com.music4all.Music4All.dtos.JwtAuthenticationResponse;
import com.music4all.Music4All.dtos.RefreshTokenRequest;
import com.music4all.Music4All.dtos.SignUpRequest;
import com.music4all.Music4All.model.User;
import com.music4all.Music4All.services.implementations.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationServiceImpl serviceImpl;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(serviceImpl.signup(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(serviceImpl.login(signUpRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(serviceImpl.refreshToken(refreshTokenRequest));
    }
}
