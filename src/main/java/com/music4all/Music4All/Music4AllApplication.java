package com.music4all.Music4All;

import com.music4all.Music4All.services.implementations.StorageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class Music4AllApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Music4AllApplication.class, args);
		System.out.println("Iniciou - Music4All");
//		String userDir = System.getProperty("user.dir");
//		String userHome = System.getProperty("user.home");
//		System.out.println("path init: " + userDir + "\\src\\main\\resources\\static\\images");
//		System.out.println("path home: " + userHome);

		StorageService storageService = context.getBean(StorageService.class);
		System.out.println(storageService.getSongFileNames());
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	// possivel nome: BandBridge
}
