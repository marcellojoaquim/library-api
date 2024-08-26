package com.books.libraryapi;

import com.books.libraryapi.service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class LibraryApiApplication {

	@Autowired
	private EmailService emailService;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public CommandLineRunner runner(){
		return args -> {
			List<String> emails = Arrays.asList("38ba663e61-b6665f@inbox.mailtrap.io");
			emailService.sendEmails("Testing email service", emails);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryApiApplication.class, args);
	}
}
