package com.example.library;

import com.example.library.dto.UserDto;
import com.example.library.model.User;
import com.example.library.model.enums.Role;
import com.example.library.service.UsersService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class LibraryApplication {

	public LibraryApplication() throws ParseException {
	}


	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}


}