package com.music4all.Music4All;

import com.music4all.Music4All.enun.Role;
import com.music4all.Music4All.model.User;
import com.music4all.Music4All.repositoriees.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class Music4AllApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Music4AllApplication.class, args);
		System.out.println("Iniciou - Music4All");
	}


	public void run(String... args) throws Exception {
		User adminAccount = userRepository.findByRole(Role.ADMIN);
		if(adminAccount == null){
			adminAccount = new User();
			adminAccount.setName("admin");
			adminAccount.setPassword(bCryptPasswordEncoder().encode("admin"));
			adminAccount.setEmail("greencodebr@gmail.com");
			adminAccount.setRole(Role.ADMIN);
			userRepository.save(adminAccount);
		}
	}
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
