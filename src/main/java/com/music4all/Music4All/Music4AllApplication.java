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
		String userDir = System.getProperty("user.dir");
		String userHome = System.getProperty("user.home");
		System.out.println("path init: " + userDir + "\\src\\main\\resources\\static\\images");
		System.out.println("path home: " + userHome);
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
