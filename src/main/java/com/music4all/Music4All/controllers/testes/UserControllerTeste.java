package com.music4all.Music4All.controllers.testes;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserControllerTeste {

    @GetMapping
    public ResponseEntity<String> teste(){
        return ResponseEntity.ok("teste user page");
    }
}
