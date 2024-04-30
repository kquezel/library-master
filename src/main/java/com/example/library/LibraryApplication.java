package com.example.library;

import com.example.library.dto.UserDto;
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
	private static final ThreadLocal<SimpleDateFormat> dateFormat =
			ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy"));
	Date birthDate = dateFormat.get().parse("03.03.1992");

	public LibraryApplication() throws ParseException {
	}


	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Bean
	public CommandLineRunner test(UsersService userService) throws ParseException {
		return (args) -> {
			userService.registerNewUserAccount(new UserDto("reader", "654321", Role.READER, "Petrov", birthDate));
			userService.registerNewUserAccount(new UserDto("editor", "123456", Role.EDITOR, "Ivanov", birthDate));
		};
	}

}