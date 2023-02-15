package com.music4all.Music4All;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Music4AllApplication {

	public static void main(String[] args) {
		SpringApplication.run(Music4AllApplication.class, args);
		System.out.println("Iniciou - Music4All");
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
