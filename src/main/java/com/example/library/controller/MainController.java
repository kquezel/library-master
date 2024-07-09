package com.example.library.controller;

import com.example.library.dto.AuthorDto;
import com.example.library.dto.UserDto;
import com.example.library.model.Author;
import com.example.library.model.User;
import com.example.library.model.enums.Role;
import com.example.library.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.example.library.model.enums.Role.READER;


@Controller
public class MainController {
    private static final ThreadLocal<SimpleDateFormat> dateFormat =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy"));
    @Autowired
    UsersService usersService;
    @Autowired
    private PasswordEncoder passwordEncoder;

        @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная странница");
        return "home";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
            UserDto newUser = new UserDto();
            model.addAttribute("user", newUser);
            return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@Valid @ModelAttribute("user") UserDto newUser, BindingResult result,
                                   @RequestParam String fullName, @RequestParam String birth,
                                   @RequestParam String password, @RequestParam String username, Model model)
            throws ParseException {
            if(result.hasErrors()) {
                model.addAttribute("user", newUser);
                return "registration";
            }
            Date birthDate = dateFormat.get().parse(birth);
            User user = new User(fullName, birthDate, true, passwordEncoder.encode(password), READER, username);
            usersService.create(user);
            return "redirect:/login";
    }
}
